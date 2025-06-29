# [Test-Module] feign-dns-lookup-issue

# Environment
- java 21
- springboot 3.5.3
- spring cloud 2025.0.0

---

# How to Run
## 1. Set `feign-dns-local.address.com` in `/etc/hosts`
```sh
vi /etc/hosts

# Feign Clients Dns Local Test
127.0.0.1       feign-dns-local.address.com
#8.8.8.8         feign-dns-local.address.com
```

## 2. For Tracking Test, Modify JVM Network TTL in (`java.security`)
This file `java.security`
```shell
cat $JAVA_HOME/conf/security/java.security | grep ttl
```

For Tracking test, _**Modify** `java.security`_

Below Like this
```shell
#networkaddress.cache.ttl=-1
# CUSTOM Value "positive ttl"
networkaddress.cache.ttl=0

#networkaddress.cache.negative.ttl=10
# Custom Value "negative ttl"
networkaddress.cache.negative.ttl=0
```

---

## 3. Run Application Server
For Running `sender-server` JVM option.
```shell
--add-exports java.base/sun.net=ALL-UNNAMED
```

__AND__ Simply just run `receiver-server`

---

## 4. Excepted `Scenario`
```mermaid
sequenceDiagram
    participant App as Feign Client
    participant JVM as JVM
    participant Pool as Connection Pool
    participant DNS as DNS/hosts

    %% First Request (For Success)
    App->>JVM: Resolve feign-dns-local.address.com
    JVM->>DNS: DNS lookup (hosts or nameserver)
    DNS-->>JVM: 127.0.0.1
    JVM-->>App: InetAddress(127.0.0.1)
    App->>Pool: Open connection to 127.0.0.1
    Pool-->>App: Connection pooled (keep-alive)

    Note over App,Pool: Next request uses pooled connection<br>no fresh DNS lookup

    %% Modify `/etc/hosts`
    Note over DNS: Modify `/etc/hosts` → 8.8.8.8

    App->>Pool: Send another request
    Pool-->>App: Reuse existing connection<br>(127.0.0.1)

    Note over App: IP change ignored due to pooled connection

    %% Force close `Connection pool`
    App->>Pool: Force pool.close()
    Pool-->>App: Pool cleared

    App->>JVM: Resolve feign-dns-local.address.com
    JVM->>DNS: DNS lookup
    DNS-->>JVM: 8.8.8.8
    JVM-->>App: InetAddress(8.8.8.8)
    App->>Pool: Open new connection to 8.8.8.8
    Pool-->>App: Connection pooled (8.8.8.8)
```

---

# Reference

## Website
- https://bugs.openjdk.org/browse/JDK-8219993
 
## Shutdown OS Cache

| OS          | 명령어                                                                 | 설명                                     |
|-------------|---------------------------------------------------------------------|----------------------------------------|
| **macOS**   | `sudo killall -HUP mDNSResponder`                                   | macOS DNS 캐시를 즉시 플러시                   |
| **Linux**   | `sudo systemd-resolve --flush-caches`<br>또는<br>`sudo nscd -i hosts` | Linux의 systemd-resolved 또는 nscd 캐시 초기화 |
| **Windows** | `ipconfig /flushdns`                                                | Windows DNS 클라이언트 캐시 초기화               |

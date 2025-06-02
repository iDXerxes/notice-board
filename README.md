Open JDK 21, Spring boot, JPA

Multi Module Architecture
[admin-api]
- 관리자 api
- controller, service
[core]
- base 모듈로 모든 공통 소스
- dto, util
[domain]
- DB와 직접적으로 통신
- Entity, repository

  admin-api 실

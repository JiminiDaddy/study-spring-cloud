# study-spring-cloud
For Spring-Cloud-Project skill-up

# Structure
Eureka-Server (port:8761)
- 서비스 디스커버리
  
  Api-Gateway (port:8080)
- 서비스 게이트웨이, 인증
    
Post-Service (port:8100)
- 게시물 작성  
    
Comment-Service (port:8200)
- 작성된 게시물에 댓글 작성  
    
# 예제 목표
1. 각 마이크로서비스들이 Eureka에 의해 동기적 통신이 가능해야한다.
2. 클라이언트는 API-Gateway를 통해서만 마이크로서비스에 접근할 수 있다.
3. 댓글이 작성/변경/삭제되면 CommentService는 메시지큐로 메시지를 보낸다.
4. PostService는 메시지큐를 통해 댓글의 변경여부를 수신한다.
5. PostService는 댓글을 Redis를 통해 캐싱한다.
6. API-Gateway는 OAuth 방식을 통해 인증을 구현해야 한다.
7. 각 마이크로서비스는 수평분할이 가능해야하며 클라이언트의 요청은 리본에 의해 분산처리되어야 한다.
8. PostService는 회로 차단기를 통해 서비스가 지연될경우 다른 인스턴스로 돌아갈 수 있어야 한다.

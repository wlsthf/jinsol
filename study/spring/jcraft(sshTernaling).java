바로 디비에 커넥션을 하면 되는데 왜 굳이 sshTernaling을 통해 커넥션을 하는가에 대해서는
데이터베이스에 외부 접근은 막혀있고 ssh 포트만 열려 있는 상태에서 개발을 해야 하는 경우가 발생 헀고
외부에서 개발하기 위해 디비커넥션이 필요했기 때문에 jcraft를 찾아보고 적용하게 되었다.



1. 먼저 pom.xml에 jcraft 사용을 위한 의존성을 주입 해준다.

	<!-- WebListnener annotation-->  이 부분은 존재할 수도 있으니 필요시 추가하면 된다
	<dependency>
		<groupId>javax.servlet</groupId>
		<artifactId>javax.servlet-api</artifactId>
		<version>4.0.1</version>
		<scope>provided</scope>
	</dependency>

	<!--  jcraft 의존성 주입 -->
	<dependency>
		<groupId>com.jcraft</groupId>
		<artifactId>jsch</artifactId>
		<version>0.1.53</version>
	</dependency>



2. Listener를 통해 이벤트발생시 실행될 디비 커넥션을 등록한다.

	@WebListener
	public class MyContextListener implements ServletContextListener {

		private SshConnection sshConnection;

		@Override
		public void contextInitialized(ServletContextEvent sce) {
			//커넥션 시작
			try {
				sshConnection = new SshConnection();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void contextDestroyed(ServletContextEvent sce) {
			//커넥션 종료
			sshConnection.closeSsh();
		}
	}



3. sshTernaling을 위해 접속 정보를 입력할 클래스를 작성한다.

	public class SshConnection {
		private final static String HOST = "ip";            //ssh 연결할 IP        ex) 123.123.123.123
		private final static Integer PORT = "port" ;        //ssh가 열려있는 포트   ex)  22
		private final static String SSH_USER = "id";        //접속 계정            ex)  root
		private final static String SSH_PW = "password";    //계정 비밀번호         ex)  1234

		private Session session;                            //import com.jcraft.jsch.Session

		//커넥션 종료
		public void closeSsh() {
			session.disconnect();
		}

		//커넥션 시작
		public SshConnection() {
			try {
				Properties config = new Properties();               // ssh 연결시 key 체크를 하지 않겠다는 properti를 등록하기 위해 객체 생성
				config.put("StrictHostKeyChecking", "no");          // properti 등록
				JSch jsch = new JSch();                             // 터널링을 위한 객체 생성
				session = jsch.getSession(SSH_USER, HOST, PORT);    // jsch.getSession("접속계정", "주소", "포트");
				session.setPassword(SSH_PW);                        // 접속계정 비밀번호
				session.setConfig(config);                          // 키사용X 주입
				session.connect();                                  // 연결
				session.setPortForwardingL(1234, "0.0.0.0", 5678);  // 127.0.0.1/1234로 접근한 포트를 0.0.0.0/5678로 포트포워딩 경우에따라 필요시 사용하면 된다.
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}


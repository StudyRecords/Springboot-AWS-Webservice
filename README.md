[![Springboot-AWS-Webservice](https://github.com/StudyRecords/Springboot-AWS-Webservice/actions/workflows/workflow.yml/badge.svg)](https://github.com/StudyRecords/Springboot-AWS-Webservice/actions/workflows/workflow.yml)

# CI/CD 구축 과정 정리

## 1️⃣ 배포 환경 구성
### 1. EC2 인스턴스
- EC2 인스턴스 생성, pem키를 통해 서버에 접속하기
- 보안 그룹 설정
- 탄력적 IP 할당, 연결
- EC2 서버 설정 : 자바 설치, 타임존 설정, 호스트 네임 변경 등
### 2. RDS
- RDS 인스턴스 생성, 운영 환경에 맞게 파라미터 설정하기
- VPC 보안 그룹 설정 (EC2에서 RDS에 접속할 수 있도록)
- DataGrip에서 RDS 접속

## 2️⃣ 서비스 배포
- 깃 설치
- 깃허브에서 소스코드 clone하기, 빌드
- EC2 서버에 배포 스크립트 만들기 `deploy.sh`
- application.yml은 깃허브에 올리면 안돼서 복사해서 EC2에 그대로 생성(붙여넣기)

## 3️⃣ 자동 배포 설정
### 1. GitHub Actions로 CI 연동하기
- .github/workflows/workflow.yml 파일에 이벤트와 job 등록하기
### 2. GitHub Actions와 AWS S3 연동하기
- AWS IAM에서 사용자 생성 (외부에서 S3에 접근할 수 있게 해줌 = 엑세스 키 발급받기)
- 엑세스 키를 github secret에 등록
- AWS S3 버킷 생성
- S3를 적용하여 workflow.yml 수정
  - **빌드된 파일들을 zip 파일로 만들고**
  - **github에 저장된 secret key 를 사용하여 AWS에 접속하고**
  - **S3에 저장** 하는 job 추가
### 3. CodeDeploy와 AWS S3, GitHub Actions 연동하기
- EC2에 적용할 IAM 역할 생성 (EC2가 S3와 CodeDeploy에 접근할 수 있게 해줌), EC2에 역할 적용
- EC2 서버에 CodeDeploy-agent 설치 (codeDeploy에 배포하기 위함, 배포 로그를 보기 위함)
- CodeDeploy에 적용할 IAM 역할 생성 (S3에 저장한 파일들을 CodeDeploy가 사용하기 위함)
- CodeDeploy에서 애플리케이션 생성
- EC2 서버에서 zip파일(빌드된 파일+설정yaml파일)을 저장할 디렉토리 생성
- 인텔리제이 플젝에 appspec.yml 파일 새성 (이 파일에서 CodeDeploy = 어떻게 배포할지를 설정)
- CodeDeploy를 적용하여 workflow.yml 수정
### 4. 배포 자동화 구성
- 배포 자동화 구성 전 설정 (배포 시 이전 버전을 중단시키기 위해 application.yml과 Application 클래스 수정)
- 인텔리제이 플젝에 deploy.sh 파일 생성
- appspec.yml 파일 수정 (codeDeploy가 자동 배포할 수 있도록 수정)
  - 파일들에 ec2-user 권한 부여
  - deploy.sh 실행시키기
- workflow.yml 파일 수정
  - Generate deployment package이란 job에서 배포에 필요한 파일들 (jar, appspec.yml, 배포 스크립트 파일 등)을 압축이 풀린 파일 형태로 가져오기 위함 (압축을 푼 파일들을 복사해서 zip 디렉토리에 가져온다)

## 4️⃣ Nginx를 이용한 무중단 배포
- EC2 서버에 Nginx 설치, 실행
- EC2 보안그룹에 Nginx 포트번호(80) 추가
- nginx.conf 파일 수정 : 스프링 부트와 Nginx 연동하기 (location 설정 추가)
- 배포 시 어떤 포트를 사용할지 기준을 명시한 API 작성하기 
- nginx.conf 파일 수정 : 배포 때마다 nginx의 프록시 설정이 교체되도록 설정하기
- 무중단 배포 스크립트 작성하기, appspec에 스크립트 파일들 적용
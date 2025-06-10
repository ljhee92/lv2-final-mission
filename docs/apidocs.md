# API 명세

## 👤 관리자와 사용자
### 🚪 로그인
- 요청
    ```json
    POST /login HTTP/1.1
      
    {
      "email": "admin@email.com"
      "password": "password"
    }
    ```
- 응답
    ```json
    HTTP/1.1 200
    Content-Type: application/json
    Set-Cookie: token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.cwnHsltFeEtOzMHs2Q5-ItawgvBZ140OyWecppNlLoI; Path=/; HttpOnly
    ```

### 👋 로그아웃
- 요청
    ```json
    POST /logout HTTP/1.1
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    ```
- 응답
    ```json
    HTTP/1.1 200
    ```
  
### ✅ 로그인 확인
- 요청
    ```json
    GET /login/check HTTP/1.1
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    ```
- 응답
    ```json
    HTTP/1.1 200
    Content-Type: application/json
    
    {
        "id": "1",
        "name": "관리자",
        "email": "admin@email.com"
    }
    ```
  
## 📚 도서
### 🧑‍💼 관리자 - 도서 검색
- 요청
    ```json
    GET /admin/books?title=오브젝트
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    ```
- 응답
    ```json
    HTTP/1.1 200
    Content-Type: application/json
    
    [
        {
          "title": "오브젝트",
          "author": "조영호 (지은이)",
          "pubDate": "2019-06-17",
          "description": "역할, 책임, 협력에 기반해 객체지향 프로그램을 설계하고 구현하는 방법, 응집도와 결합도를 이용해 설계를 트레이드오프하는 방법, 설계를 유연하게 만드는 다양한 의존성 관리 기법, 타입 계층을 위한 상속과 코드 재사용을 위한 합성의 개념 등을 다룬다.",
          "image": "https://image.aladin.co.kr/product/19368/10/coversum/k972635015_1.jpg",
          "isbn": "K972635015"
        },
        {
          "title": "쿼드러플 오브젝트",
          "author": "그레이엄 하먼 (지은이), 주대중 (옮긴이), 서동진 (해제)",
          "pubDate": "2019-11-30",  
          "description": "컨템포러리 총서. 현 시대의 가장 도발적인 철학자 그레이엄 하먼은 주저 『쿼드러플 오브젝트』를 통해 인간중심주의의 맹점을 폭로한다. 무엇보다 그는 그동안 철학의 중심에서 배제되었던 사물/대상/객체야말로 사유의 한가운데 자리 잡아야 한다고 역설한다.",
          "image": "https://image.aladin.co.kr/product/21776/51/coversum/8965642280_1.jpg",
          "isbn": "8965642280"
        }   
    ]
    ```

### 🧑‍💼 관리자 - 도서 등록
- 요청
    ```json
    POST /admin/books
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    
    {
        "title": "오브젝트",
        "author": "조영호 (지은이)",
        "pubDate": "2019-06-17",
        "description": "역할, 책임, 협력에 기반해 객체지향 프로그램을 설계하고 구현하는 방법, 응집도와 결합도를 이용해 설계를 트레이드오프하는 방법, 설계를 유연하게 만드는 다양한 의존성 관리 기법, 타입 계층을 위한 상속과 코드 재사용을 위한 합성의 개념 등을 다룬다.",
        "image": "https://image.aladin.co.kr/product/19368/10/coversum/k972635015_1.jpg",
        "isbn": "K972635015",
        "totalQuantity": "2"
    }
    ```
- 응답
    ```json
    HTTP/1.1 201
    Content-Type: application/json
    
    {
        "id": 1,
        "title": "오브젝트",
        "author": "조영호 (지은이)",
        "pubDate": "2019-06-17",
        "description": "역할, 책임, 협력에 기반해 객체지향 프로그램을 설계하고 구현하는 방법, 응집도와 결합도를 이용해 설계를 트레이드오프하는 방법, 설계를 유연하게 만드는 다양한 의존성 관리 기법, 타입 계층을 위한 상속과 코드 재사용을 위한 합성의 개념 등을 다룬다.",
        "image": "https://image.aladin.co.kr/product/19368/10/coversum/k972635015_1.jpg",
        "isbn": "K972635015",
        "totalQuantity": "2"
    }
    ```

### 🧑‍💼 관리자 - 등록된 도서 리스트 조회
- 요청
    ```json
    GET /admin/books
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    ```
- 응답
    ```json
    HTTP/1.1 200
    Content-Type: application/json
    
    [
        {
          "id": "1",
          "title": "오브젝트",
          "author": "조영호 (지은이)",
          "pubDate": "2019-06-17",
          "description": "역할, 책임, 협력에 기반해 객체지향 프로그램을 설계하고 구현하는 방법, 응집도와 결합도를 이용해 설계를 트레이드오프하는 방법, 설계를 유연하게 만드는 다양한 의존성 관리 기법, 타입 계층을 위한 상속과 코드 재사용을 위한 합성의 개념 등을 다룬다.",
          "image": "https://image.aladin.co.kr/product/19368/10/coversum/k972635015_1.jpg",
          "isbn": "K972635015",
          "availableQuantity": "0",
          "totalQuantity": "2",
          "availableRentalDate": "2025-06-12"
        },
        {
          "id": "2",
          "title": "좋은 코드, 나쁜 코드 - 프로그래머의 코드 품질 개선법, 2023년 세종도서 학술부문 추천도서",
          "author": "톰 롱 (지은이), 차건회 (옮긴이)",
          "pubDate": "2022-05-26",  
          "description": "구글 엔지니어가 말하는 좋은 코드 작성법. 좋은 코드를 작성하기 위한 이론과 실전을 소개한다. 단순히 해야 할 일과 하지 말아야 할 일을 나열하기보다, 여섯 가지 원칙을 바탕으로 각 개념과 기술의 장단점, 그리고 이면의 핵심 논리를 설명한다.",
          "image": "https://image.aladin.co.kr/product/29464/92/coversum/k422837236_1.jpg",
          "isbn": "K422837236",
          "availableQuantity": "2",
          "totalQuantity": "2",
          "availableRentalDate": "2025-06-10"
        }   
    ]
    ```

### 🧑‍💼 관리자 - 도서 삭제
- 요청
    ```json
    DELETE /admin/books/1 HTTP/1.1
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    ```
- 응답
    ```json
    HTTP/1.1 204
    ```

### 👧 사용자 - 등록된 도서 리스트 조회
- 요청
    ```json
    GET /books
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    ```
- 응답
    ```json
    HTTP/1.1 200
    Content-Type: application/json
    
    [
        {
          "id": "1",
          "title": "오브젝트",
          "author": "조영호 (지은이)",
          "pubDate": "2019-06-17",
          "description": "역할, 책임, 협력에 기반해 객체지향 프로그램을 설계하고 구현하는 방법, 응집도와 결합도를 이용해 설계를 트레이드오프하는 방법, 설계를 유연하게 만드는 다양한 의존성 관리 기법, 타입 계층을 위한 상속과 코드 재사용을 위한 합성의 개념 등을 다룬다.",
          "image": "https://image.aladin.co.kr/product/19368/10/coversum/k972635015_1.jpg",
          "isbn": "K972635015",
          "availableQuantity": "0",
          "totalQuantity": "2",
          "availableRentalDate": "2025-06-12"
        },
        {
          "id": "2",
          "title": "좋은 코드, 나쁜 코드 - 프로그래머의 코드 품질 개선법, 2023년 세종도서 학술부문 추천도서",
          "author": "톰 롱 (지은이), 차건회 (옮긴이)",
          "pubDate": "2022-05-26",  
          "description": "구글 엔지니어가 말하는 좋은 코드 작성법. 좋은 코드를 작성하기 위한 이론과 실전을 소개한다. 단순히 해야 할 일과 하지 말아야 할 일을 나열하기보다, 여섯 가지 원칙을 바탕으로 각 개념과 기술의 장단점, 그리고 이면의 핵심 논리를 설명한다.",
          "image": "https://image.aladin.co.kr/product/29464/92/coversum/k422837236_1.jpg",
          "isbn": "K422837236",
          "availableQuantity": "2",
          "totalQuantity": "2",
          "availableRentalDate": "2025-06-10"
        }   
    ]
    ```


## 💻 예약
### 🧑‍💼 관리자 - 도서 예약 리스트 조회
- 요청
    ```json
    GET /admin/reservations
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    ```
- 응답
    ```json
    HTTP/1.1 200
    Content-Type: application/json
    
    [
        {
          "id": "1",
          "email": "member1@email.com",
          "title": "오브젝트",
          "author": "조영호 (지은이)",
          "rentalDate": "2025-06-07",
          "returnDate": "2025-06-11"
        },
        {
          "id": "2",
          "email": "member2@email.com",
          "title": "오브젝트",
          "author": "조영호 (지은이)",
          "rentalDate": "2025-06-10",
          "returnDate": "2025-06-17"
        }   
    ]
    ```

### 🧑‍💼 관리자 - 도서 예약 취소
- 요청
    ```json
    DELETE /admin/reservations/1 HTTP/1.1
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    ```
- 응답
    ```json
    HTTP/1.1 204
    ```

### 👧 사용자 - 도서 예약 생성
- 요청
    ```json
    POST /reservations
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    
    {
        "email": "member1@email.com",
        "retnalDate": "2025-06-10",
        "returnDate": "2025-06-17",
        "isbn": "K422837236"
    }
    ```
- 응답
    ```json
    HTTP/1.1 201
    Content-Type: application/json
    
    {
        "id": 1,
        "email": "member1@email.com",
        "retnalDate": "2025-06-10",
        "returnDate": "2025-06-17",
        "isbn": "K422837236"
    }
    ```

### 👧 사용자 - 예약 도서 상세 정보 조회
- 요청
    ```json
    GET /reservations-mine HTTP/1.1
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    ```
- 응답
    ```json
    HTTP/1.1 200
    Content-Type: application/json
    
    [
        {
          "id": "1",
          "title": "오브젝트",
          "author": "조영호 (지은이)",
          "rentalDate": "2025-06-07",
          "returnDate": "2025-06-11",
          "status": "예약"
        },
        {
          "id": "2",
          "title": "좋은 코드, 나쁜 코드 - 프로그래머의 코드 품질 개선법, 2023년 세종도서 학술부문 추천도서",
          "author": "톰 롱 (지은이), 차건회 (옮긴이)",
          "rentalDate": "2025-06-10",
          "returnDate": "2025-06-17",
          "status": "예약취소"
        }   
    ]
    ```

### 👧 사용자 - 도서 예약 취소
- 요청
    ```json
    DELETE /reservations/1 HTTP/1.1
    cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0; Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164; token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
    ```
- 응답
    ```json
    HTTP/1.1 204
    ```
  
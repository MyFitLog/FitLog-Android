# FITLOG - 운동 일정 기록 서비스

---

## 프로젝트 소개

---

 - Android에서 달력에 운동 일정을 기록하는 서비스 개발을 목표 
 - Room을 이용하여 내부 DB에 데이터를 저장(v1.0) 
 - Spring서버를 이용하여 DB에 데이터를 저장(v2.0) 

---

### 프로젝트 목표

 - orbit 라이브러리를 이용한 MVI패턴 적용 경험
 - koin을 이용한 DI 적용


## 기술 스택

* kotlin 1.9.23
* compose_ui 1.9.0
* orbig 7.1.0
* koin 3.6.0-wasm-alpha2
* room 2.6.1
* retrofit2 2.11.0

## 프로젝트 파일 구조

---

```
com
└── example
    └── fitlog
        ├── common
        ├── data
        │   ├── model
        │   │   └── exercise
        ├── di
        └── ui
            ├── add
            │   └── composable
            ├── calendar
            │   └── composable
            └── theme
```


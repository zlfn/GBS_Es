현재 개발중인 버전입니다.
==========================

## Feature development info
### 완료

 - 기본 UI 제작
 - 자가진단 자동화 플러그인과의 연동
 - 학습실 신청 자동화 프로그램 API 제작
 - 기초적인 display 기능

### 진행 예정

 - 학습실 신청 자동화 플러그인과의 연동
 - 학습실 다중신청 자동화
 - Server side program develop
 - UI/UX
 - 분실물 찾기 fragment develop
 - 자가진단, 학습실 신청, 수행평가 관련 알림 푸시(w. GCP, FCM)
 - Crawl meal data in https://gbs.hs.kr
 - Create setting fragment(fragment exist but that has no action)

### Deprecated

 - Feature which makes able to move legacy launcher: 신런쳐의 완성도 상승시 삭제 예정. 관련 코드는 lgs_MainActivity.kt에 존재

## File Explanation

### 1. com.dayo.executer.ui
#### Created Activity / fragment
   - This fragments are called by navigation bar
   - HomeFragment.kt: Fragment which will displays first(call by default fragment in bottom navigation bar)
   - SettingsFragment.kt: Fragment which allows user to edit settings
#### Not Created Activity / fragment
   - FindLostThingsFragment.kt: 분실물 찾기 fragment
   - EditAblrListFragment.kt: Fragment which makes ablr list editable


### 2. com.dayo.executer
#### Created Activity
   - MainActivity.kt: Which activity displays first
   - ~lgs_MainActivity.kt (Deprecated): Which activity used by old version~


## Server side Launcher logic
 - Parse Data from comsi.kr: node.js
 - Open server: Apache Web Server


## Client side Launcher logic
 - Launcher: Kotlin

## Developer
 - Every dev or designer need to edit this part yourself
 - Dayo
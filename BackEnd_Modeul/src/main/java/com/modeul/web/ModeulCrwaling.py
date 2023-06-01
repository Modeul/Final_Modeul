from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time , pyautogui , pyperclip
import pymysql
import requests
from bs4 import BeautifulSoup

# DB 연결
db = pymysql.connect(host="oracle.newlecture.com", port=3306,
           user="gamja" , password="potato",
           charset="utf8")
cursor = db.cursor()

# INSERT 쿼리 생성
insert = '''
INSERT INTO `gamjadb`.`crawling`
(`title`,
`price`,
`contenturl`,
`imgurl`,
`category_id`,
`categoryname`)
VALUES
(%s, %s, %s, %s, %s, %s)
'''
# 테이블 내용 전체 삭제
sql = 'TRUNCATE TABLE `gamjadb`.`crawling`'
cursor.execute(sql)
db.commit()


# 크롬드라이버 자동 업데이트
from webdriver_manager.chrome import ChromeDriverManager

# 브라우저 자동 꺼짐 방지
chrome_options = Options()
chrome_options.add_experimental_option("detach",True)

# 불필요한 에러 메세지 없애기
chrome_options.add_experimental_option("excludeSwitches",["enable-logging"])
# 브라우저 백그라운드로띄우기
# chrome_options.add_argument("headless")

# 셀레니움으로 웹브라우저를 자동으로 띄운다 그래야 제어하고 자동화시킬수있다.

service = Service(executable_path=ChromeDriverManager().install())
# 크롬드라이버 매니저를 통해서 설치한다 (최신버전을 알아서 가져옴) 그리고 service 객체로 만든다.

driver = webdriver.Chrome(service=service , options=chrome_options)
# 셀레니움웹드라이버에서 크롬을 만들어낼거다. 서비스에는 위에 선언한 서비스를 넣어준다. 옵션도 마찬가지.

driver.implicitly_wait(5) # 웹페이지가 로딩 될때까지 5초는 기다린다.
# driver.maximize_window() # 화면을 최대화 시킨다.


n = 0

driver.get("https://www.coupang.com/np/categories/194276") # 쿠팡
items = driver.find_elements(By.CLASS_NAME,"baby-product-link") # 상품 정보 목록
for item in items:
    time.sleep(0.1)
    title = item.find_element(By.CLASS_NAME,'name').text
    price = item.find_element(By.CLASS_NAME,'price-value').text
    link = item.get_attribute('href')
    img = item.find_element(By.CSS_SELECTOR,'img').get_attribute('src')
    categoryname = '쿠팡'
    print(f"{n}번째 상품명:{title}\n가격:{price}\n링크:{link}\n이미지:{img}\n")
    n = n + 1
    # INSERT 쿼리 실행
    cursor.execute(insert, (title, price, link, img, 1,categoryname))
    if n == len(items):
        n = 0
        break
driver.get("https://www.coupang.com/np/categories/194276?page=2") # 쿠팡
items = driver.find_elements(By.CLASS_NAME,"baby-product-link") # 상품 정보 목록
for item in items:
    time.sleep(0.05)
    title = item.find_element(By.CLASS_NAME,'name').text
    price = item.find_element(By.CLASS_NAME,'price-value').text
    link = item.get_attribute('href')
    img = item.find_element(By.CSS_SELECTOR,'img').get_attribute('src')
    categoryname = '쿠팡'
    print(f"{n}번째 상품명:{title}\n가격:{price}\n링크:{link}\n이미지:{img}\n")
    n = n + 1
    # INSERT 쿼리 실행
    cursor.execute(insert, (title, price, link, img, 1,categoryname))
    if n == len(items):
        n = 0
        break
print("=================================쿠팡==========================================")



driver.get("https://www.gmarket.co.kr/n/best?&viewType=C&largeCategoryCode=100000020") # G마켓
items = driver.find_elements(By.CSS_SELECTOR,"a.itemname")
for item in items:
    title = item.text
    link = item.get_attribute('href')
    response = requests.get(link , headers={'User-agent':'Mozila/5.0'})
    html = response.text
    soup = BeautifulSoup(html , 'html.parser')
    price = soup.select_one('.price_real').text
    view = soup.select_one('ul.viewer')
    images = view.select('img')
    if len(images) > 1:
        img = images[1].attrs['src']
    elif len(images) == 1:
        img = images[0].attrs['src']
    categoryname = 'G마켓'
    print(f"{n}번째 상품명:{title}\n가격:{price}\n링크:{link}\n이미지:{img}\n")
    n = n + 1
    # INSERT 쿼리 실행
    cursor.execute(insert, (title, price, link, img, 2,categoryname))
    if n == len(items):
        n = 0
        break
print("=================================G마켓==========================================")



driver.get("https://www.ssg.com/service/bestMain.ssg?zoneId=6000078977") # 이마트
items = driver.find_elements(By.CLASS_NAME,"cunit_t290")
for item in items:
    time.sleep(0.05)
    titleinfo = item.find_elements(By.CLASS_NAME,'tx_ko')
    if len(titleinfo) > 1:
        title = titleinfo[1].text
    else : title = titleinfo[0].text
    price = item.find_element(By.CLASS_NAME,'ssg_price').text
    link = item.find_element(By.TAG_NAME,'a').get_attribute('href')
    img = item.find_element(By.CSS_SELECTOR,'img').get_attribute('src')
    categoryname = '이마트'
    print(f"{n}번째 상품명:{title}\n가격:{price}\n링크:{link}\n이미지:{img}\n")
    n = n + 1
    # INSERT 쿼리 실행
    cursor.execute(insert, (title, price, link, img, 3,categoryname))
    if n == len(items):
        n = 0
        break
driver.get("https://www.ssg.com/service/bestMain.ssg?zoneId=5000016022") # 이마트
items = driver.find_elements(By.CLASS_NAME,"cunit_t290")
for item in items:
    time.sleep(0.05)
    titleinfo = item.find_elements(By.CLASS_NAME,'tx_ko')
    if len(titleinfo) > 1:
        title = titleinfo[1].text
    else : title = titleinfo[0].text
    price = item.find_element(By.CLASS_NAME,'ssg_price').text
    link = item.find_element(By.TAG_NAME,'a').get_attribute('href')
    img = item.find_element(By.CSS_SELECTOR,'img').get_attribute('src')
    categoryname = '이마트'
    print(f"{n}번째 상품명:{title}\n가격:{price}\n링크:{link}\n이미지:{img}\n")
    n = n + 1
    # INSERT 쿼리 실행
    cursor.execute(insert, (title, price, link, img, 3,categoryname))
    if n == len(items):
        n = 0
        break
print("=================================이마트==========================================")

driver.get("https://shopping.interpark.com/best/main.do?&smid1=s_menu&smid2=best") # 인터파크
items = driver.find_elements(By.CLASS_NAME,"goods")
for item in items:
    time.sleep(0.05)
    title = item.find_element(By.CLASS_NAME,'name').text
    price = item.find_element(By.CLASS_NAME,'number').text
    link = item.find_element(By.TAG_NAME,'a').get_attribute('href')
    img = item.find_element(By.CSS_SELECTOR,'img').get_attribute('src')
    categoryname = '인터파크'
    print(f"{n}번째 상품명:{title}\n가격:{price}\n링크:{link}\n이미지:{img}\n")
    n = n + 1
    # INSERT 쿼리 실행
    cursor.execute(insert, (title, price, link, img, 4,categoryname))
    if n == 200:
        n = 0
        break
print("=================================인터파크==========================================")


driver.get("https://corners.auction.co.kr/corner/categorybest.aspx") #옥션
items = driver.find_elements(By.CLASS_NAME,"img-list")
for item in items:
    time.sleep(0.05)
    title = item.find_element(By.TAG_NAME,'em').text
    price = item.find_element(By.CLASS_NAME,'sale').text
    link = item.find_element(By.TAG_NAME,'a').get_attribute('href')
    img = item.find_element(By.CLASS_NAME,'line').get_attribute('src')
    categoryname = '옥션'
    print(f"{n}번째 상품명:{title}\n가격:{price}\n링크:{link}\n이미지:{img}\n")
    n = n + 1
    # INSERT 쿼리 실행
    cursor.execute(insert, (title, price, link, img, 5,categoryname))
    if n == 200:
        n = 0
        break
print("=================================옥션==========================================")

driver.get("https://www.oliveyoung.co.kr/store/main/getBestList.do") #올리브영
items = driver.find_elements(By.CLASS_NAME,"prd_info")
for item in items:
    time.sleep(0.05)
    title = item.find_element(By.CLASS_NAME,'tx_name').text
    price = item.find_element(By.CLASS_NAME,'tx_num').text
    link = item.find_element(By.TAG_NAME,'a').get_attribute('href')
    img = item.find_element(By.TAG_NAME,'img').get_attribute('src')
    categoryname = '올리브영'
    print(f"{n}번째 상품명:{title}\n가격:{price}\n링크:{link}\n이미지:{img}\n")
    n = n + 1
    # INSERT 쿼리 실행
    cursor.execute(insert, (title, price, link, img, 6,categoryname))
    if n == len(items):
        n = 0
        break
print("=================================올리브영==========================================")


db.commit()
db.close()
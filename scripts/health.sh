# !/usr/bin/env bash
# nginx와 연결되어 있지 않은 스프링 부트가 잘 수행되는지 체크 -> 확인 성공한 경우에만 프록시 설정을 변경 (switch.sh에 있는 switch_proxy 사용)
ASBPATH = $(readlink -f $0)
ABSDIR = $(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT = $(find_idle_port)

echo ">> Health Check Start!"
echo ">> IDLE_PORT: $IDLE_PORT"
echo ">> curl -s http://localhost:$IDLE_PORT/profile"
sleep 10

for RETRY_COUNT in {1..10}
do
    RESPONSE = $(curl -s http://localhost:${IDLE_PORT}/profile)
    UP_COUNT = $(echo ${RESPONSE} | grep 'real' | wc -l)

    if [${UP_COUNT} -ge 1]      # (UP_COUNT >= 1) == ("real" 문자열이 있는지 검증)
    then
        echo ">> Health check 성공"
        switch_proxy
        break
    else
        echo ">> Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다."
        echo ">> Health check: ${RESPONSE}"
    fi

    if [${RETRY_COUNT} -eq 10]
    then
        echo ">> Health check 실패."
        echo ">> Nginx에 연결하지 않고 배포를 종료합니다."
        exit 1
    fi

    echo ">> Health check 연결 실패. 재시도..."
    sleep
done
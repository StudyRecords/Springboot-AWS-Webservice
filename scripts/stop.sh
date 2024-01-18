# !/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)        # stop.sh가 속해 있는 경로를 찾는다. (profile.sh의 경로를 찾기 위해 사용된다.)
source ${ABSDIR}/profile.sh          # 자바의 import문과 같은 역할을 한다.

IDLE_PORT=$(find_idle_port)
echo ">> $IDLE_PORT 에서 구동 중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [-z ${IDLE_PID}]
then echo ">> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else echo ">> kill -15 $IDLE_PID"
    kill -15 ${IDLE_PID}
    sleep 5
fi
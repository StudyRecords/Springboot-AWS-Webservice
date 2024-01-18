# !/usr/bin/env bash
ASBPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy(){
    IDLE_PORT=$(find_idle_port)

    echo ">> 전환한 Port: $IDLE_PORT"
    echo ">> Port 전환"
    # 하나의 문장을 만들어 파이프라인(|)으로 넘겨주기 위해 echo를 사용한다.
    # nginx가 변경할 프록시 주소를 생성한다.
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
    # sudo 부분 -> 파이프라인 앞에서 넘겨준 문장을 service_url.inc에 덮어쓴다

    # restart : 잠시 끊기는 현상이 있다. reload : 끊김 없이 다시 불러온다. 다만 중요한 설정들은 반영되지 않음 (여기선 외부 설정 파일인 service-url을 다시 불러오는 거라 reload로 가능)
    echo ">> Nginx Reload"
    sudo service nginx reload
}
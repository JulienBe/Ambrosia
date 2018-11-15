#!/usr/bin/env bash

for i in 1 2 #3 #4 5 6 7 8 9
do
    echo "run $i"
    gradle run > bench"$i" &
    sleep 120
    pid=`ps -aux | grep DesktopLauncher | grep -v grep | cut -d' ' -f9`
    pid4numbers=`ps -aux | grep DesktopLauncher | grep -v grep | cut -d' ' -f10`
    kill -9 "$pid"
    kill -9 "$pid4numbers"
done

analyse() {
    total=0
    iter=0
    for line in $@
    do
        total=$((total + 1))
        iter=$((iter + $line))
    done
    avg=`bc <<< "scale=1; $iter / $total"`
    echo -e " \t$avg        iter $iter total $total"
}

grep_system() {
    echo "grep for $2 on $1"
    `grep "$2" "$1" | cut -d'F' -f2`
}
for i in 1 2 #3 #4 5 6 7 8 9
do
    fps=`grep fps bench"$i" | cut -d']' -f2 | sed 's/ //g'`
    mvt=`grep "movement" "bench$i" | cut -d'F' -f2`
    sensor=`grep "sensor" "bench$i" | cut -d'F' -f2`
    binder=`grep "Bind" "bench$i" | cut -d'F' -f2`
    col=`grep "coll" "bench$i" | cut -d'F' -f2`
    draw=`grep "draw" "bench$i" | cut -d'F' -f2`
    time=`grep "tim" "bench$i" | cut -d'F' -f2`
    echo "======= ITERATION $i"
    echo "fps       : `analyse ${fps}`"
    echo "movement  : `analyse ${mvt} `"
    echo "sensor    : `analyse ${sensor} `"
    echo "binder    : `analyse ${binder} `"
    echo "collision : `analyse ${col} `"
    echo "draw      : `analyse ${draw} `"
    echo "time      : `analyse ${time} `"
done


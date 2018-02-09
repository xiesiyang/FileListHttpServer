#!/bin/sh
java -jar ./target/file_ls_http_server-1.0-SNAPSHOT.jar &
echo $! > ./jar.pid


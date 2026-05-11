@echo off
cd /d %~dp0
cd ..
start http://localhost:5173
docker-compose up --build
pause
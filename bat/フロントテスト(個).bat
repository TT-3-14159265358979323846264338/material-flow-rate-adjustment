@echo off
cd /d %~dp0
cd ..
set /p filename="Enter test file name (e.g., Login): "
docker compose exec frontend npx vitest run %filename%
pause
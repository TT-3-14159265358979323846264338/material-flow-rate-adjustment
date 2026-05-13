@echo off
cd /d %~dp0
cd ..
docker compose exec frontend npx vitest
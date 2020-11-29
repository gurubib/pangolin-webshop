docker-compose -f docker/docker-compose.yml up --build -d
docker exec pangolin-client rails webpacker:install
cp webshop-client/config/webpack_environment_copy.js webshop-client/config/webpack/environment.js

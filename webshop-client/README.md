# README

This README would normally document whatever steps are necessary to get the
application up and running.

## Deployment instructions

### Locally, without SSL

You will need the following things before starting:
* Ruby version: 2.6.5

To install dependencies, run this command:
`bundle install`

To start webserver:
`rails server`

### Docker, with SSL

#### For the very first time
First, you have to generate a self-signed certificate in the `config/` folder.
```
cd config/
openssl req -x509 -sha256 -nodes -newkey rsa:2048 -days 365 -keyout localhost.key -out localhost.crt
```

Build from the base directory:
```
docker build webshop-client -t pangolin-client
```

Execute this command:
`docker exec pangolin-client rails webpacker:install` 

Then run the image:
```
docker run -p 3000:3000 pangolin-client rails s -b ssl://0.0.0.0:3000?key=config/localhost.key&cert=config/localhost.crt
```

#### After the first time
Build from the base directory:
```
docker build webshop-client -t pangolin-client
```

Then run the image:
```
docker run -p 3000:3000 pangolin-client rails s -b ssl://0.0.0.0:3000?key=config/localhost.key&cert=config/localhost.crt
```
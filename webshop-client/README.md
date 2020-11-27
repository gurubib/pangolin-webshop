# README

This README would normally document whatever steps are necessary to get the
application up and running.

## Deployment instructions

### Locally

You will need the following things before starting:
* Ruby version: 2.6.5

To install dependencies, run this command:
`bundle install`

To start webserver:
`rails server`

### With Docker

Build from the base directory:

```
docker build webshop-client -t pangolin-client
```

**Important**: on first run after executing the above command run this as well:
`docker exec calsy-client rails webpacker:install` 

Then run the image:

```
docker run -p 3000:3000 pangolin-client rails s -b 0.0.0.0
```
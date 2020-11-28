FROM ruby:2.6.5-alpine

RUN apk add --update --no-cache bash build-base nodejs npm sqlite-dev tzdata postgresql-dev yarn
RUN apk add curl-dev

RUN gem install bundler:2.1.4

WORKDIR /usr/src/app

RUN npm install
COPY package.json yarn.lock ./
RUN yarn install --check-files

COPY Gemfile* ./
RUN bundle install

COPY . .

ENV PATH=./bin:$PATH
CMD ["rails", "console"]

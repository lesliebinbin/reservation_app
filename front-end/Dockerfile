# base image
FROM node

# set working directory

# add `/app/node_modules/.bin` to $PATH
#ENV PATH /app/node_modules/.bin:$PATH

WORKDIR /app
COPY package.json .
# install and cache app dependencies
RUN npm install --silent

# start app
CMD ["npm", "start"]

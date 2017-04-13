const webpack = require('webpack');
const path = require('path');
const buildPath = path.resolve(__dirname, 'src/main/webapp/script');
const nodeModulesPath = path.resolve(__dirname, 'node_modules');

const config = {
  entry: [
    path.join(__dirname, '/src/main/javascript/portal.js'),
  ],
  output: {
    path: buildPath,
    filename: 'portal.js',
  },
  module: {
    loaders: [
      {
        test: /\.js$/,
        loaders: ['babel-loader'],
        exclude: [nodeModulesPath],
      },
    ],
  },
};

module.exports = config;

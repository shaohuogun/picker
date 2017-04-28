import React from 'react';
import ReactDOM from 'react-dom';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Paper from 'material-ui/Paper';

import RequestList from './RequestList';

const leftStyle = {
  width: 800,
  marginLeft: 20,
  float: 'left',
  display: 'inline-block',
};

const rightStyle = {
  width: 350,
  marginLeft: 10,
  float: 'left',
  display: 'inline-block',
};

export default class RequestPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  render() {
    return (
      <div>
      <MuiThemeProvider muiTheme={getMuiTheme()}>
      <RequestList style={leftStyle} />
      </MuiThemeProvider>

      <MuiThemeProvider muiTheme={getMuiTheme()}>
      <Paper style={rightStyle} zDepth={1} />
      </MuiThemeProvider>
      </div>
    );
  }

};

RequestPage.propTypes = {
};

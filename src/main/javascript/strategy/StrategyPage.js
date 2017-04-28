import React from 'react';
import ReactDOM from 'react-dom';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

import StrategyList from './StrategyList';
import StrategyForm from './StrategyForm';

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

export default class StrategyPage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    };
  }

  render() {
    return (
      <div>
      <MuiThemeProvider muiTheme={getMuiTheme()}>
      <StrategyList style={leftStyle} />
      </MuiThemeProvider>

      <MuiThemeProvider muiTheme={getMuiTheme()}>
      <StrategyForm style={rightStyle} />
      </MuiThemeProvider>
      </div>
    );
  }

};

StrategyPage.propTypes = {
};

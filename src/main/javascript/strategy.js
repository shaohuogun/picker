import $ from "jquery";
import React from 'react';
import ReactDOM from 'react-dom';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import injectTapEventPlugin from 'react-tap-event-plugin';
import AppBar from 'material-ui/AppBar';
import {Card, CardActions, CardHeader, CardTitle, CardText} from 'material-ui/Card';

import StrategyList from './strategy/StrategyList';
import StrategyForm from './strategy/StrategyForm';

injectTapEventPlugin();

const strategyListStyle = {
  width: 300,
  margin: 20,
  float: 'left',
  display: 'inline-block',
};

class App extends React.Component {
  constructor(props) {
    super(props);
    // 设置 initial state
    this.state = {
      strategies: [],
    };
  }

  loadMessagesFromServer() {
    var self = this;

    $.ajax({
      url: "/api/strategy",
    }).then(function(data) {
      self.setState({ strategies: data });
    });
  }

  componentDidMount() {
    this.loadMessagesFromServer();
  }

  render() {
    return (
      <div>
      <MuiThemeProvider muiTheme={getMuiTheme()}>
      <AppBar title="采集平台" />
      </MuiThemeProvider>

      <br />

      <MuiThemeProvider muiTheme={getMuiTheme()}>
      <Card style={strategyListStyle}>
      <CardHeader title="采集策略" />
      <StrategyList strategies={this.state.strategies} />
      </Card>
      </MuiThemeProvider>

      <MuiThemeProvider muiTheme={getMuiTheme()}>
      <StrategyForm/>
      </MuiThemeProvider>

      </div>
    );
  }

};

App.propTypes = {
  initialValue: React.PropTypes.string
};

App.defaultProps = {
  initialValue: ''
};

let app = document.createElement('div');
ReactDOM.render(<App />, app);
document.body.appendChild(app);

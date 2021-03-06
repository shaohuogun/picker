import React from 'react'
import PropTypes from 'prop-types'
import {render} from 'react-dom'
import injectTapEventPlugin from 'react-tap-event-plugin'
import {BrowserRouter as Router, browserHistory, Route, IndexRoute, Link, Redirect} from 'react-router-dom'
import {MuiThemeProvider, createMuiTheme} from 'material-ui/styles'
import AppBar from 'material-ui/AppBar'

import PortalPage from './portal/PortalPage'
import StrategyPage from './strategy/StrategyPage'
import RequestPage from './request/RequestPage'

const theme = createMuiTheme()

// Needed for onTouchTap
injectTapEventPlugin()

const CustomLink = ({activeOnlyWhenExact, to, label}) => (
  <Route exact={activeOnlyWhenExact} path={to} children={({match}) => (
    <span>
    {match ? '[' : ''}<Link to={to}>{label}</Link>{match ? ']' : ''}
    </span>
  )}/>
)

export class Layout extends React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    return (
      <div>
      <MuiThemeProvider theme={theme}>
      <AppBar title="采集平台" />
      </MuiThemeProvider>

      <ul>
      <li><CustomLink activeOnlyWhenExact={true} to="/" label="首页"/></li>
      <li><CustomLink to="/strategy" label="策略"/></li>
      <li><CustomLink to="/request" label="需求"/></li>
      </ul>
      {this.props.children}
      </div>
    )
  }
}

Layout.propTypes = {
}

export default class Picker extends React.Component {
  constructor(props) {
    super(props)
  }

  render() {
    return (
      <Router history={browserHistory}>
      <Layout>
      <Route exact path="/" component={PortalPage}/>
      <Route path="/strategy" component={StrategyPage}/>
      <Route path="/request" component={RequestPage}/>
      </Layout>
      </Router>
    )
  }
}

let root = document.createElement('div')
render(<Picker />, root)
document.body.appendChild(root)

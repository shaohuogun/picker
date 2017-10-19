import React from 'react'
import PropTypes from 'prop-types'

import StrategyList from './StrategyList'
import StrategyForm from './StrategyForm'

const leftStyle = {
  width: 800,
  marginLeft: 20,
  float: 'left',
  display: 'inline-block',
}

const rightStyle = {
  width: 350,
  marginLeft: 10,
  float: 'left',
  display: 'inline-block',
}

export default class StrategyPage extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
    }
  }

  render() {
    return (
      <div>
      <StrategyList style={leftStyle} />
      <StrategyForm style={rightStyle} />
      </div>
    )
  }

}

StrategyPage.propTypes = {
}

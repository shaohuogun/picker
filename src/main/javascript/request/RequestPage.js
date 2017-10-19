import React from 'react'
import Paper from 'material-ui/Paper'
import RequestList from './RequestList'

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

export default class RequestPage extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
    }
  }

  render() {
    return (
      <div>
      <RequestList style={leftStyle} />
      <Paper style={rightStyle} zDepth={1} />
      </div>
    )
  }

}

RequestPage.propTypes = {
}

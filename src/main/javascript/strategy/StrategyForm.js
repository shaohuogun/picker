import $ from "jquery";
import React from 'react';
import {Card, CardActions, CardHeader, CardTitle, CardText} from 'material-ui/Card';
import FlatButton from 'material-ui/FlatButton';
import TextField from 'material-ui/TextField';

const strategyFormStyle = {
  width: 800,
  margin: 20,
  float: 'left',
  display: 'inline-block',
};

const toolbarStyle = {
  textAlign: 'center',
};

export default class StrategyForm extends React.Component {
  constructor(props) {
    super(props);
    // 设置 initial state
    this.state = {
      expanded: false,
      name: "",
      urlRegex: "",
      xml: "",
    };
  }

  handleExpandChange = (expanded) => {
    this.setState({expanded: expanded});
  };

  handleFieldChange = (event, newValue) => {
    var fieldId = event.target.id;
    if (fieldId == "name") {
      this.setState({name: newValue});
    } else if (fieldId == "urlRegex") {
      this.setState({urlRegex: newValue});
    } else if (fieldId == "xml") {
      this.setState({xml: newValue});
    }
  };

  handleCancel = () => {
    this.setState({
      expanded: false,
      name: "",
      urlRegex: "",
      xml: "",
    });
  };

  handleSubmit = () => {
    var data = JSON.stringify({
      name: this.state.name,
      urlRegex: this.state.urlRegex,
      xml: this.state.xml,
    });

    $.ajax({
      type: "POST",
      url: "/api/strategy",
      data: data,
      contentType: "application/json;charset=utf-8",
      dataType: "json"
    }).then(function() {
      this.setState({expanded: {false}});
    });
  };

  render() {
    return (
      <Card style={strategyFormStyle} expanded={this.state.expanded} onExpandChange={this.handleExpandChange}>
      <CardHeader
      title="新增采集策略"
      actAsExpander={true}
      showExpandableButton={true}
      />
      <CardText expandable={true}>
      <TextField
      id="name"
      value={this.state.name}
      hintText="csdn-channel"
      floatingLabelText="策略名称"
      fullWidth={true}
      onChange={this.handleFieldChange}
      />
      <br />
      <TextField
      id="urlRegex"
      value={this.state.urlRegex}
      hintText="(http://)?blog.csdn.net/([a-zA-Z0-9]+)?/article/list"
      floatingLabelText="匹配规则"
      fullWidth={true}
      onChange={this.handleFieldChange}
      />
      <br />
      <TextField
      id="xml"
      value={this.state.xml}
      multiLine={true}
      floatingLabelText="策略内容"
      fullWidth={true}
      onChange={this.handleFieldChange}
      />
      </CardText>
      <CardActions style={toolbarStyle} expandable={true}>
      <FlatButton label="取消" onTouchTap={this.handleCancel} />
      <FlatButton label="提交" onTouchTap={this.handleSubmit} />
      </CardActions>
      </Card>
    );
  }

};

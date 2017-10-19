import $ from "jquery";
import React from 'react';
import PropTypes from 'prop-types';
import Button from 'material-ui/Button'
import {Card, CardHeader, CardText, CardActions} from 'material-ui/Card';
import {ListItem, List} from 'material-ui/List';
import Divider from 'material-ui/Divider';
import Pagination from 'material-ui-pagination';

const actionStyle = {
	textAlign: 'right',
};

const toolbarStyle = {
	textAlign: 'center',
};

export class RequestListItem extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			pagination: {},
		};
	}

	handleExpandChange(newExpandedState) {
		if (!newExpandedState) {
			return;
		}

    var self = this;
    $.ajax({
      url: "/api/request/" + self.props.request.id + "/replies",
			type: "GET",
			data: {},
    }).then(function(data) {
      self.setState({
        pagination: data,
      });
    });
  }

	redoRequest = (requestId) => {
		var self = this;
		$.ajax({
			url: "/api/request/" + requestId + "/redo",
			type: "GET",
		  data: {},
		}).then(function(data) {

		});
  }

	render() {
		var request = this.props.request;
		var actions = [];
		if ((request.status == "suspended") || (request.status == "error")) {
			actions.push(
				<CardActions style={actionStyle}>
				<Button
				label="重新执行"
				secondary={true}
				onTouchTap={this.redoRequest.bind(this, request.id)}
				/>
				</CardActions>
			);
		}

		return (
			<Card
			{...this.props}
			zDepth={0}
			onExpandChange={this.handleExpandChange.bind(this)}
			>
			<CardHeader
			title={request.content}
			subtitle={<span>类型：{request.actionType}   状态：{request.status}   开始：{request.startTime}   结束：{request.endTime}</span>}
			actAsExpander={true}
			showExpandableButton={true}
			/>
			{actions}
			<CardText expandable={true}>
			{this.state.pagination.total}
			</CardText>
			</Card>
		);
	}

};

RequestListItem.propTypes = {
	request: PropTypes.object.isRequired,
};


export default class RequestList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			pagination: {},
		};
	}

	loadMessages = (page) => {
		var self = this;
		$.ajax({
			url: "/api/requests",
			type: "GET",
			data: {
				page: page.toString(),
			},
		}).then(function(data) {
			self.setState({
				pagination: data,
			});
		});
	}

	componentDidMount() {
		this.loadMessages(1);
	}

	handleItemCLick = (requestId) => {

	}

	render() {
		var requests = this.state.pagination.objects;
		if (requests == null) {
			return (<Card {...this.props} zDepth={1}></Card>);
		}

		var rows = [];
		var requestCount = requests.length;
		for (var i = 0; i < requestCount; i++) {
			var request = requests[i];
			rows.push(
				<RequestListItem key={request.id} request={request} />
			);

			if (i < (requestCount - 1)) {
				rows.push(<Divider key={request.id} />);
			}
		}

		return (
			<Card {...this.props} zDepth={1}>
			<CardHeader title="需求列表" />
			<CardText>
			{rows}
			</CardText>
			<CardActions style={toolbarStyle}>
			<Pagination
			total = {this.state.pagination.pageCount}
			current = {this.state.pagination.pageIndex}
			display = {this.state.pagination.pageShow}
			onChange = {current => this.loadMessages(current)}
			/>
			</CardActions>
			</Card>
		);
	}

};

RequestList.propTypes = {
};

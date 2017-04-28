import $ from "jquery";
import React from 'react';
import PropTypes from 'prop-types';
import FlatButton from 'material-ui/FlatButton';
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
			result: {},
		};
	}

	handleExpandChange(newExpandedState) {
		if (!newExpandedState) {
			return;
		}

    var self = this;
    $.ajax({
      url: "/api/result/" + self.props.request.resultId,
			type: "GET",
			data: {},
    }).then(function(data) {
      self.setState({
        result: data,
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
				<FlatButton
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
			title={request.targetUrl}
			subtitle={<span>类型：{request.targetType}   状态：{request.status}   开始：{request.startTime}   结束：{request.endTime}</span>}
			actAsExpander={true}
			showExpandableButton={true}
			/>
			{actions}
			<CardText expandable={true}>
			{this.state.result.json}
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

	loadPagination = (page) => {
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
		this.loadPagination(1);
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
				<RequestListItem request={request} />
			);

			if (i < (requestCount - 1)) {
				rows.push(<Divider />);
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
			onChange = {current => this.loadPagination(current)}
			/>
			</CardActions>
			</Card>
		);
	}

};

RequestList.propTypes = {
};

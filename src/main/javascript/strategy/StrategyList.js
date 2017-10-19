import $ from "jquery";
import React from 'react';
import PropTypes from 'prop-types';
import Button from 'material-ui/Button'
import {Card, CardHeader, CardText, CardActions} from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import Pagination from 'material-ui-pagination';

const actionStyle = {
	textAlign: 'right',
};

const toolbarStyle = {
	textAlign: 'center',
};

export class StrategyListItem extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
		};
	}

	deleteStrategy = (strategyId) => {
		var self = this;
		$.ajax({
			url: "/api/strategy/" + strategyId,
			type: "DELETE",
		  data: {},
		}).then(function(data) {

		});
  }

	render() {
		var strategy = this.props.strategy;
		return (
			<Card
			{...this.props}
			zDepth={0}
			>
			<CardHeader
			title={strategy.name}
			subtitle={strategy.urlRegex}
			actAsExpander={true}
			showExpandableButton={true}
			/>
			<CardActions style={actionStyle}>
			<Button
			label="删除"
			secondary={true}
			onTouchTap={this.deleteStrategy.bind(this, strategy.id)}
			/>
			</CardActions>
			<CardText expandable={true}>
			<textarea
			style={{
				width: '100%',
				height: '200px',
				border:'none',
			}}
			>
			{strategy.xml}
			</textarea>
			</CardText>
			</Card>
		);
	}

};

StrategyListItem.propTypes = {
	strategy: PropTypes.object.isRequired,
};


export default class StrategyList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			pagination: {},
		};
	}

	loadMessages = (page) => {
		var self = this;
		$.ajax({
			url: "/api/strategies",
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

	handleItemCLick = (strategyId) => {

	}

	render() {
		var strategies = this.state.pagination.objects;
		if (strategies == null) {
			return (<Card {...this.props} zDepth={1}></Card>);
		}

		var rows = [];
		var strategyCount = strategies.length;
		for (var i = 0; i < strategyCount; i++) {
			var strategy = strategies[i];
			rows.push(
				<StrategyListItem key={strategy.id} strategy={strategy} />
			);

			if (i < (strategyCount - 1)) {
				rows.push(<Divider key={strategy.id} />);
			}
		}

		return (
			<Card {...this.props} zDepth={1}>
			<CardHeader title="策略列表" />
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

StrategyList.propTypes = {
};

import $ from "jquery";
import React from 'react';
import PropTypes from 'prop-types';
import {Card, CardHeader, CardText, CardActions} from 'material-ui/Card';
import {ListItem, List} from 'material-ui/List';
import Divider from 'material-ui/Divider';
import Pagination from 'material-ui-pagination';

const paginationStyle = {
	textAlign: 'center',
};

export default class StrategyList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			pagination: {},
		};
	}

	loadPagination = (page) => {
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
		this.loadPagination(1);
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
        <ListItem
        primaryText={strategy.name}
        onTouchTap={this.handleItemCLick.bind(this, strategy.id)}
        />
			);

			if (i < (strategyCount - 1)) {
				rows.push(<Divider />);
			}
		}

		return (
			<Card {...this.props} zDepth={1}>
			<CardHeader title="策略列表" />
			<CardText>
			<List>
			{rows}
			</List>
			</CardText>
			<CardActions style={paginationStyle}>
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

StrategyList.propTypes = {
};

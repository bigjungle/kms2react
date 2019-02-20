import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, reset } from './query-common-10-sdm-suffix.reducer';
import { IQueryCommon10SdmSuffix } from 'app/shared/model/query-common-10-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IQueryCommon10SdmSuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IQueryCommon10SdmSuffixState extends IPaginationBaseState {
  search: string;
}

export class QueryCommon10SdmSuffix extends React.Component<IQueryCommon10SdmSuffixProps, IQueryCommon10SdmSuffixState> {
  state: IQueryCommon10SdmSuffixState = {
    search: '',
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  search = () => {
    if (this.state.search) {
      this.props.reset();
      this.setState({ activePage: 1 }, () => {
        const { activePage, itemsPerPage, sort, order, search } = this.state;
        this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
      });
    }
  };

  clear = () => {
    this.props.reset();
    this.setState({ search: '', activePage: 1 }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order, search } = this.state;
    if (search) {
      this.props.getSearchEntities(search, activePage - 1, itemsPerPage, `${sort},${order}`);
    } else {
      this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
    }
  };

  render() {
    const { queryCommon10List, match } = this.props;
    return (
      <div>
        <h2 id="query-common-10-sdm-suffix-heading">
          <Translate contentKey="kmsApp.queryCommon10.home.title">Query Common 10 S</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="kmsApp.queryCommon10.home.createLabel">Create new Query Common 10</Translate>
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('kmsApp.queryCommon10.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('queryName')}>
                    <Translate contentKey="kmsApp.queryCommon10.queryName">Query Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('queryDate')}>
                    <Translate contentKey="kmsApp.queryCommon10.queryDate">Query Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('queryUser')}>
                    <Translate contentKey="kmsApp.queryCommon10.queryUser">Query User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col01')}>
                    <Translate contentKey="kmsApp.queryCommon10.col01">Col 01</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col02')}>
                    <Translate contentKey="kmsApp.queryCommon10.col02">Col 02</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col03')}>
                    <Translate contentKey="kmsApp.queryCommon10.col03">Col 03</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col04')}>
                    <Translate contentKey="kmsApp.queryCommon10.col04">Col 04</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col05')}>
                    <Translate contentKey="kmsApp.queryCommon10.col05">Col 05</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col06')}>
                    <Translate contentKey="kmsApp.queryCommon10.col06">Col 06</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col07')}>
                    <Translate contentKey="kmsApp.queryCommon10.col07">Col 07</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col08')}>
                    <Translate contentKey="kmsApp.queryCommon10.col08">Col 08</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col09')}>
                    <Translate contentKey="kmsApp.queryCommon10.col09">Col 09</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col10')}>
                    <Translate contentKey="kmsApp.queryCommon10.col10">Col 10</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {queryCommon10List.map((queryCommon10, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${queryCommon10.id}`} color="link" size="sm">
                        {queryCommon10.id}
                      </Button>
                    </td>
                    <td>{queryCommon10.queryName}</td>
                    <td>
                      <TextFormat type="date" value={queryCommon10.queryDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{queryCommon10.queryUser}</td>
                    <td>{queryCommon10.col01}</td>
                    <td>{queryCommon10.col02}</td>
                    <td>{queryCommon10.col03}</td>
                    <td>{queryCommon10.col04}</td>
                    <td>{queryCommon10.col05}</td>
                    <td>{queryCommon10.col06}</td>
                    <td>{queryCommon10.col07}</td>
                    <td>{queryCommon10.col08}</td>
                    <td>{queryCommon10.col09}</td>
                    <td>{queryCommon10.col10}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${queryCommon10.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${queryCommon10.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${queryCommon10.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ queryCommon10 }: IRootState) => ({
  queryCommon10List: queryCommon10.entities,
  totalItems: queryCommon10.totalItems,
  links: queryCommon10.links,
  entity: queryCommon10.entity,
  updateSuccess: queryCommon10.updateSuccess
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueryCommon10SdmSuffix);

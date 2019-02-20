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
import { getSearchEntities, getEntities, reset } from './query-common-50-sdm-suffix.reducer';
import { IQueryCommon50SdmSuffix } from 'app/shared/model/query-common-50-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IQueryCommon50SdmSuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IQueryCommon50SdmSuffixState extends IPaginationBaseState {
  search: string;
}

export class QueryCommon50SdmSuffix extends React.Component<IQueryCommon50SdmSuffixProps, IQueryCommon50SdmSuffixState> {
  state: IQueryCommon50SdmSuffixState = {
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
    const { queryCommon50List, match } = this.props;
    return (
      <div>
        <h2 id="query-common-50-sdm-suffix-heading">
          <Translate contentKey="kmsApp.queryCommon50.home.title">Query Common 50 S</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="kmsApp.queryCommon50.home.createLabel">Create new Query Common 50</Translate>
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
                    placeholder={translate('kmsApp.queryCommon50.home.search')}
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
                    <Translate contentKey="kmsApp.queryCommon50.queryName">Query Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('queryDate')}>
                    <Translate contentKey="kmsApp.queryCommon50.queryDate">Query Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('queryUser')}>
                    <Translate contentKey="kmsApp.queryCommon50.queryUser">Query User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col01')}>
                    <Translate contentKey="kmsApp.queryCommon50.col01">Col 01</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col02')}>
                    <Translate contentKey="kmsApp.queryCommon50.col02">Col 02</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col03')}>
                    <Translate contentKey="kmsApp.queryCommon50.col03">Col 03</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col04')}>
                    <Translate contentKey="kmsApp.queryCommon50.col04">Col 04</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col05')}>
                    <Translate contentKey="kmsApp.queryCommon50.col05">Col 05</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col06')}>
                    <Translate contentKey="kmsApp.queryCommon50.col06">Col 06</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col07')}>
                    <Translate contentKey="kmsApp.queryCommon50.col07">Col 07</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col08')}>
                    <Translate contentKey="kmsApp.queryCommon50.col08">Col 08</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col09')}>
                    <Translate contentKey="kmsApp.queryCommon50.col09">Col 09</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col10')}>
                    <Translate contentKey="kmsApp.queryCommon50.col10">Col 10</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col11')}>
                    <Translate contentKey="kmsApp.queryCommon50.col11">Col 11</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col12')}>
                    <Translate contentKey="kmsApp.queryCommon50.col12">Col 12</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col13')}>
                    <Translate contentKey="kmsApp.queryCommon50.col13">Col 13</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col14')}>
                    <Translate contentKey="kmsApp.queryCommon50.col14">Col 14</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col15')}>
                    <Translate contentKey="kmsApp.queryCommon50.col15">Col 15</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col16')}>
                    <Translate contentKey="kmsApp.queryCommon50.col16">Col 16</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col17')}>
                    <Translate contentKey="kmsApp.queryCommon50.col17">Col 17</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col18')}>
                    <Translate contentKey="kmsApp.queryCommon50.col18">Col 18</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col19')}>
                    <Translate contentKey="kmsApp.queryCommon50.col19">Col 19</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col20')}>
                    <Translate contentKey="kmsApp.queryCommon50.col20">Col 20</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col21')}>
                    <Translate contentKey="kmsApp.queryCommon50.col21">Col 21</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col22')}>
                    <Translate contentKey="kmsApp.queryCommon50.col22">Col 22</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col23')}>
                    <Translate contentKey="kmsApp.queryCommon50.col23">Col 23</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col24')}>
                    <Translate contentKey="kmsApp.queryCommon50.col24">Col 24</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col25')}>
                    <Translate contentKey="kmsApp.queryCommon50.col25">Col 25</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col26')}>
                    <Translate contentKey="kmsApp.queryCommon50.col26">Col 26</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col27')}>
                    <Translate contentKey="kmsApp.queryCommon50.col27">Col 27</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col28')}>
                    <Translate contentKey="kmsApp.queryCommon50.col28">Col 28</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col29')}>
                    <Translate contentKey="kmsApp.queryCommon50.col29">Col 29</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col30')}>
                    <Translate contentKey="kmsApp.queryCommon50.col30">Col 30</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col31')}>
                    <Translate contentKey="kmsApp.queryCommon50.col31">Col 31</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col32')}>
                    <Translate contentKey="kmsApp.queryCommon50.col32">Col 32</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col33')}>
                    <Translate contentKey="kmsApp.queryCommon50.col33">Col 33</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col34')}>
                    <Translate contentKey="kmsApp.queryCommon50.col34">Col 34</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col35')}>
                    <Translate contentKey="kmsApp.queryCommon50.col35">Col 35</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col36')}>
                    <Translate contentKey="kmsApp.queryCommon50.col36">Col 36</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col37')}>
                    <Translate contentKey="kmsApp.queryCommon50.col37">Col 37</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col38')}>
                    <Translate contentKey="kmsApp.queryCommon50.col38">Col 38</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col39')}>
                    <Translate contentKey="kmsApp.queryCommon50.col39">Col 39</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col40')}>
                    <Translate contentKey="kmsApp.queryCommon50.col40">Col 40</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col41')}>
                    <Translate contentKey="kmsApp.queryCommon50.col41">Col 41</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col42')}>
                    <Translate contentKey="kmsApp.queryCommon50.col42">Col 42</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col43')}>
                    <Translate contentKey="kmsApp.queryCommon50.col43">Col 43</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col44')}>
                    <Translate contentKey="kmsApp.queryCommon50.col44">Col 44</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col45')}>
                    <Translate contentKey="kmsApp.queryCommon50.col45">Col 45</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col46')}>
                    <Translate contentKey="kmsApp.queryCommon50.col46">Col 46</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col47')}>
                    <Translate contentKey="kmsApp.queryCommon50.col47">Col 47</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col48')}>
                    <Translate contentKey="kmsApp.queryCommon50.col48">Col 48</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col49')}>
                    <Translate contentKey="kmsApp.queryCommon50.col49">Col 49</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('col50')}>
                    <Translate contentKey="kmsApp.queryCommon50.col50">Col 50</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {queryCommon50List.map((queryCommon50, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${queryCommon50.id}`} color="link" size="sm">
                        {queryCommon50.id}
                      </Button>
                    </td>
                    <td>{queryCommon50.queryName}</td>
                    <td>
                      <TextFormat type="date" value={queryCommon50.queryDate} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{queryCommon50.queryUser}</td>
                    <td>{queryCommon50.col01}</td>
                    <td>{queryCommon50.col02}</td>
                    <td>{queryCommon50.col03}</td>
                    <td>{queryCommon50.col04}</td>
                    <td>{queryCommon50.col05}</td>
                    <td>{queryCommon50.col06}</td>
                    <td>{queryCommon50.col07}</td>
                    <td>{queryCommon50.col08}</td>
                    <td>{queryCommon50.col09}</td>
                    <td>{queryCommon50.col10}</td>
                    <td>{queryCommon50.col11}</td>
                    <td>{queryCommon50.col12}</td>
                    <td>{queryCommon50.col13}</td>
                    <td>{queryCommon50.col14}</td>
                    <td>{queryCommon50.col15}</td>
                    <td>{queryCommon50.col16}</td>
                    <td>{queryCommon50.col17}</td>
                    <td>{queryCommon50.col18}</td>
                    <td>{queryCommon50.col19}</td>
                    <td>{queryCommon50.col20}</td>
                    <td>{queryCommon50.col21}</td>
                    <td>{queryCommon50.col22}</td>
                    <td>{queryCommon50.col23}</td>
                    <td>{queryCommon50.col24}</td>
                    <td>{queryCommon50.col25}</td>
                    <td>{queryCommon50.col26}</td>
                    <td>{queryCommon50.col27}</td>
                    <td>{queryCommon50.col28}</td>
                    <td>{queryCommon50.col29}</td>
                    <td>{queryCommon50.col30}</td>
                    <td>{queryCommon50.col31}</td>
                    <td>{queryCommon50.col32}</td>
                    <td>{queryCommon50.col33}</td>
                    <td>{queryCommon50.col34}</td>
                    <td>{queryCommon50.col35}</td>
                    <td>{queryCommon50.col36}</td>
                    <td>{queryCommon50.col37}</td>
                    <td>{queryCommon50.col38}</td>
                    <td>{queryCommon50.col39}</td>
                    <td>{queryCommon50.col40}</td>
                    <td>{queryCommon50.col41}</td>
                    <td>{queryCommon50.col42}</td>
                    <td>{queryCommon50.col43}</td>
                    <td>{queryCommon50.col44}</td>
                    <td>{queryCommon50.col45}</td>
                    <td>{queryCommon50.col46}</td>
                    <td>{queryCommon50.col47}</td>
                    <td>{queryCommon50.col48}</td>
                    <td>{queryCommon50.col49}</td>
                    <td>{queryCommon50.col50}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${queryCommon50.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${queryCommon50.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${queryCommon50.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ queryCommon50 }: IRootState) => ({
  queryCommon50List: queryCommon50.entities,
  totalItems: queryCommon50.totalItems,
  links: queryCommon50.links,
  entity: queryCommon50.entity,
  updateSuccess: queryCommon50.updateSuccess
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
)(QueryCommon50SdmSuffix);

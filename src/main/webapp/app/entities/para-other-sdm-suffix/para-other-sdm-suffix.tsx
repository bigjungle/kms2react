import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import {
  openFile,
  byteSize,
  Translate,
  translate,
  ICrudSearchAction,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, reset } from './para-other-sdm-suffix.reducer';
import { IParaOtherSdmSuffix } from 'app/shared/model/para-other-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IParaOtherSdmSuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IParaOtherSdmSuffixState extends IPaginationBaseState {
  search: string;
}

export class ParaOtherSdmSuffix extends React.Component<IParaOtherSdmSuffixProps, IParaOtherSdmSuffixState> {
  state: IParaOtherSdmSuffixState = {
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
    const { paraOtherList, match } = this.props;
    return (
      <div>
        <h2 id="para-other-sdm-suffix-heading">
          <Translate contentKey="kmsApp.paraOther.home.title">Para Others</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="kmsApp.paraOther.home.createLabel">Create new Para Other</Translate>
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
                    placeholder={translate('kmsApp.paraOther.home.search')}
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
                  <th className="hand" onClick={this.sort('name')}>
                    <Translate contentKey="kmsApp.paraOther.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('serialNumber')}>
                    <Translate contentKey="kmsApp.paraOther.serialNumber">Serial Number</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('sortString')}>
                    <Translate contentKey="kmsApp.paraOther.sortString">Sort String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('descString')}>
                    <Translate contentKey="kmsApp.paraOther.descString">Desc String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('paraOtherValueType')}>
                    <Translate contentKey="kmsApp.paraOther.paraOtherValueType">Para Other Value Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('paraValueSt')}>
                    <Translate contentKey="kmsApp.paraOther.paraValueSt">Para Value St</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('paraValueIn')}>
                    <Translate contentKey="kmsApp.paraOther.paraValueIn">Para Value In</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('paraValueBo')}>
                    <Translate contentKey="kmsApp.paraOther.paraValueBo">Para Value Bo</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('paraValueJs')}>
                    <Translate contentKey="kmsApp.paraOther.paraValueJs">Para Value Js</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('paraValueBl')}>
                    <Translate contentKey="kmsApp.paraOther.paraValueBl">Para Value Bl</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlob')}>
                    <Translate contentKey="kmsApp.paraOther.imageBlob">Image Blob</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlobName')}>
                    <Translate contentKey="kmsApp.paraOther.imageBlobName">Image Blob Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('usingFlag')}>
                    <Translate contentKey="kmsApp.paraOther.usingFlag">Using Flag</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('remarks')}>
                    <Translate contentKey="kmsApp.paraOther.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validType')}>
                    <Translate contentKey="kmsApp.paraOther.validType">Valid Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validBegin')}>
                    <Translate contentKey="kmsApp.paraOther.validBegin">Valid Begin</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validEnd')}>
                    <Translate contentKey="kmsApp.paraOther.validEnd">Valid End</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('createTime')}>
                    <Translate contentKey="kmsApp.paraOther.createTime">Create Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('modifyTime')}>
                    <Translate contentKey="kmsApp.paraOther.modifyTime">Modify Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('verifyTime')}>
                    <Translate contentKey="kmsApp.paraOther.verifyTime">Verify Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraOther.createdUser">Created User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraOther.modifiedUser">Modified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraOther.verifiedUser">Verified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {paraOtherList.map((paraOther, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${paraOther.id}`} color="link" size="sm">
                        {paraOther.id}
                      </Button>
                    </td>
                    <td>{paraOther.name}</td>
                    <td>{paraOther.serialNumber}</td>
                    <td>{paraOther.sortString}</td>
                    <td>{paraOther.descString}</td>
                    <td>
                      <Translate contentKey={`kmsApp.ParaOtherValueType.${paraOther.paraOtherValueType}`} />
                    </td>
                    <td>{paraOther.paraValueSt}</td>
                    <td>
                      <TextFormat type="date" value={paraOther.paraValueIn} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{paraOther.paraValueBo ? 'true' : 'false'}</td>
                    <td>{paraOther.paraValueJs}</td>
                    <td>
                      {paraOther.paraValueBl ? (
                        <div>
                          <a onClick={openFile(paraOther.paraValueBlContentType, paraOther.paraValueBl)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                          <span>
                            {paraOther.paraValueBlContentType}, {byteSize(paraOther.paraValueBl)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>
                      {paraOther.imageBlob ? (
                        <div>
                          <a onClick={openFile(paraOther.imageBlobContentType, paraOther.imageBlob)}>
                            <img
                              src={`data:${paraOther.imageBlobContentType};base64,${paraOther.imageBlob}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                          <span>
                            {paraOther.imageBlobContentType}, {byteSize(paraOther.imageBlob)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{paraOther.imageBlobName}</td>
                    <td>{paraOther.usingFlag ? 'true' : 'false'}</td>
                    <td>{paraOther.remarks}</td>
                    <td>
                      <Translate contentKey={`kmsApp.ValidType.${paraOther.validType}`} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraOther.validBegin} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraOther.validEnd} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraOther.createTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraOther.modifyTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraOther.verifyTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {paraOther.createdUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraOther.createdUserId}`}>{paraOther.createdUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraOther.modifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraOther.modifiedUserId}`}>{paraOther.modifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraOther.verifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraOther.verifiedUserId}`}>{paraOther.verifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${paraOther.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${paraOther.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${paraOther.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ paraOther }: IRootState) => ({
  paraOtherList: paraOther.entities,
  totalItems: paraOther.totalItems,
  links: paraOther.links,
  entity: paraOther.entity,
  updateSuccess: paraOther.updateSuccess
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
)(ParaOtherSdmSuffix);

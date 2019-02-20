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
import { getSearchEntities, getEntities, reset } from './para-source-sdm-suffix.reducer';
import { IParaSourceSdmSuffix } from 'app/shared/model/para-source-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IParaSourceSdmSuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IParaSourceSdmSuffixState extends IPaginationBaseState {
  search: string;
}

export class ParaSourceSdmSuffix extends React.Component<IParaSourceSdmSuffixProps, IParaSourceSdmSuffixState> {
  state: IParaSourceSdmSuffixState = {
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
    const { paraSourceList, match } = this.props;
    return (
      <div>
        <h2 id="para-source-sdm-suffix-heading">
          <Translate contentKey="kmsApp.paraSource.home.title">Para Sources</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="kmsApp.paraSource.home.createLabel">Create new Para Source</Translate>
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
                    placeholder={translate('kmsApp.paraSource.home.search')}
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
                    <Translate contentKey="kmsApp.paraSource.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('serialNumber')}>
                    <Translate contentKey="kmsApp.paraSource.serialNumber">Serial Number</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('sortString')}>
                    <Translate contentKey="kmsApp.paraSource.sortString">Sort String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('descString')}>
                    <Translate contentKey="kmsApp.paraSource.descString">Desc String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlob')}>
                    <Translate contentKey="kmsApp.paraSource.imageBlob">Image Blob</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlobName')}>
                    <Translate contentKey="kmsApp.paraSource.imageBlobName">Image Blob Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('usingFlag')}>
                    <Translate contentKey="kmsApp.paraSource.usingFlag">Using Flag</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('remarks')}>
                    <Translate contentKey="kmsApp.paraSource.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validType')}>
                    <Translate contentKey="kmsApp.paraSource.validType">Valid Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validBegin')}>
                    <Translate contentKey="kmsApp.paraSource.validBegin">Valid Begin</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validEnd')}>
                    <Translate contentKey="kmsApp.paraSource.validEnd">Valid End</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('createTime')}>
                    <Translate contentKey="kmsApp.paraSource.createTime">Create Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('modifyTime')}>
                    <Translate contentKey="kmsApp.paraSource.modifyTime">Modify Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('verifyTime')}>
                    <Translate contentKey="kmsApp.paraSource.verifyTime">Verify Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraSource.createdUser">Created User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraSource.modifiedUser">Modified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraSource.verifiedUser">Verified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraSource.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {paraSourceList.map((paraSource, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${paraSource.id}`} color="link" size="sm">
                        {paraSource.id}
                      </Button>
                    </td>
                    <td>{paraSource.name}</td>
                    <td>{paraSource.serialNumber}</td>
                    <td>{paraSource.sortString}</td>
                    <td>{paraSource.descString}</td>
                    <td>
                      {paraSource.imageBlob ? (
                        <div>
                          <a onClick={openFile(paraSource.imageBlobContentType, paraSource.imageBlob)}>
                            <img
                              src={`data:${paraSource.imageBlobContentType};base64,${paraSource.imageBlob}`}
                              style={{ maxHeight: '30px' }}
                            />
                            &nbsp;
                          </a>
                          <span>
                            {paraSource.imageBlobContentType}, {byteSize(paraSource.imageBlob)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{paraSource.imageBlobName}</td>
                    <td>{paraSource.usingFlag ? 'true' : 'false'}</td>
                    <td>{paraSource.remarks}</td>
                    <td>
                      <Translate contentKey={`kmsApp.ValidType.${paraSource.validType}`} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraSource.validBegin} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraSource.validEnd} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraSource.createTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraSource.modifyTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraSource.verifyTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {paraSource.createdUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraSource.createdUserId}`}>{paraSource.createdUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraSource.modifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraSource.modifiedUserId}`}>{paraSource.modifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraSource.verifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraSource.verifiedUserId}`}>{paraSource.verifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraSource.parentName ? (
                        <Link to={`para-source-sdm-suffix/${paraSource.parentId}`}>{paraSource.parentName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${paraSource.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${paraSource.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${paraSource.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ paraSource }: IRootState) => ({
  paraSourceList: paraSource.entities,
  totalItems: paraSource.totalItems,
  links: paraSource.links,
  entity: paraSource.entity,
  updateSuccess: paraSource.updateSuccess
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
)(ParaSourceSdmSuffix);

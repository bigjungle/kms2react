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
import { getSearchEntities, getEntities, reset } from './para-type-sdm-suffix.reducer';
import { IParaTypeSdmSuffix } from 'app/shared/model/para-type-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IParaTypeSdmSuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IParaTypeSdmSuffixState extends IPaginationBaseState {
  search: string;
}

export class ParaTypeSdmSuffix extends React.Component<IParaTypeSdmSuffixProps, IParaTypeSdmSuffixState> {
  state: IParaTypeSdmSuffixState = {
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
    const { paraTypeList, match } = this.props;
    return (
      <div>
        <h2 id="para-type-sdm-suffix-heading">
          <Translate contentKey="kmsApp.paraType.home.title">Para Types</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="kmsApp.paraType.home.createLabel">Create new Para Type</Translate>
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
                    placeholder={translate('kmsApp.paraType.home.search')}
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
                    <Translate contentKey="kmsApp.paraType.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('serialNumber')}>
                    <Translate contentKey="kmsApp.paraType.serialNumber">Serial Number</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('sortString')}>
                    <Translate contentKey="kmsApp.paraType.sortString">Sort String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('descString')}>
                    <Translate contentKey="kmsApp.paraType.descString">Desc String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlob')}>
                    <Translate contentKey="kmsApp.paraType.imageBlob">Image Blob</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlobName')}>
                    <Translate contentKey="kmsApp.paraType.imageBlobName">Image Blob Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('usingFlag')}>
                    <Translate contentKey="kmsApp.paraType.usingFlag">Using Flag</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('remarks')}>
                    <Translate contentKey="kmsApp.paraType.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validType')}>
                    <Translate contentKey="kmsApp.paraType.validType">Valid Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validBegin')}>
                    <Translate contentKey="kmsApp.paraType.validBegin">Valid Begin</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validEnd')}>
                    <Translate contentKey="kmsApp.paraType.validEnd">Valid End</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('createTime')}>
                    <Translate contentKey="kmsApp.paraType.createTime">Create Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('modifyTime')}>
                    <Translate contentKey="kmsApp.paraType.modifyTime">Modify Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('verifyTime')}>
                    <Translate contentKey="kmsApp.paraType.verifyTime">Verify Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraType.createdUser">Created User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraType.modifiedUser">Modified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraType.verifiedUser">Verified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraType.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {paraTypeList.map((paraType, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${paraType.id}`} color="link" size="sm">
                        {paraType.id}
                      </Button>
                    </td>
                    <td>{paraType.name}</td>
                    <td>{paraType.serialNumber}</td>
                    <td>{paraType.sortString}</td>
                    <td>{paraType.descString}</td>
                    <td>
                      {paraType.imageBlob ? (
                        <div>
                          <a onClick={openFile(paraType.imageBlobContentType, paraType.imageBlob)}>
                            <img src={`data:${paraType.imageBlobContentType};base64,${paraType.imageBlob}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                          <span>
                            {paraType.imageBlobContentType}, {byteSize(paraType.imageBlob)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{paraType.imageBlobName}</td>
                    <td>{paraType.usingFlag ? 'true' : 'false'}</td>
                    <td>{paraType.remarks}</td>
                    <td>
                      <Translate contentKey={`kmsApp.ValidType.${paraType.validType}`} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraType.validBegin} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraType.validEnd} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraType.createTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraType.modifyTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={paraType.verifyTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      {paraType.createdUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraType.createdUserId}`}>{paraType.createdUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraType.modifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraType.modifiedUserId}`}>{paraType.modifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraType.verifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraType.verifiedUserId}`}>{paraType.verifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraType.parentName ? <Link to={`para-type-sdm-suffix/${paraType.parentId}`}>{paraType.parentName}</Link> : ''}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${paraType.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${paraType.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${paraType.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ paraType }: IRootState) => ({
  paraTypeList: paraType.entities,
  totalItems: paraType.totalItems,
  links: paraType.links,
  entity: paraType.entity,
  updateSuccess: paraType.updateSuccess
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
)(ParaTypeSdmSuffix);

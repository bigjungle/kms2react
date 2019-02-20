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
  getSortState,
  IPaginationBaseState
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities, reset } from './para-role-sdm-suffix.reducer';
import { IParaRoleSdmSuffix } from 'app/shared/model/para-role-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IParaRoleSdmSuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IParaRoleSdmSuffixState extends IPaginationBaseState {
  search: string;
}

export class ParaRoleSdmSuffix extends React.Component<IParaRoleSdmSuffixProps, IParaRoleSdmSuffixState> {
  state: IParaRoleSdmSuffixState = {
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
    const { paraRoleList, match } = this.props;
    return (
      <div>
        <h2 id="para-role-sdm-suffix-heading">
          <Translate contentKey="kmsApp.paraRole.home.title">Para Roles</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="kmsApp.paraRole.home.createLabel">Create new Para Role</Translate>
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
                    placeholder={translate('kmsApp.paraRole.home.search')}
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
                    <Translate contentKey="kmsApp.paraRole.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('serialNumber')}>
                    <Translate contentKey="kmsApp.paraRole.serialNumber">Serial Number</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('sortString')}>
                    <Translate contentKey="kmsApp.paraRole.sortString">Sort String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('descString')}>
                    <Translate contentKey="kmsApp.paraRole.descString">Desc String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlob')}>
                    <Translate contentKey="kmsApp.paraRole.imageBlob">Image Blob</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlobName')}>
                    <Translate contentKey="kmsApp.paraRole.imageBlobName">Image Blob Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('usingFlag')}>
                    <Translate contentKey="kmsApp.paraRole.usingFlag">Using Flag</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('remarks')}>
                    <Translate contentKey="kmsApp.paraRole.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraRole.createdUser">Created User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraRole.modifiedUser">Modified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraRole.verifiedUser">Verified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraRole.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {paraRoleList.map((paraRole, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${paraRole.id}`} color="link" size="sm">
                        {paraRole.id}
                      </Button>
                    </td>
                    <td>{paraRole.name}</td>
                    <td>{paraRole.serialNumber}</td>
                    <td>{paraRole.sortString}</td>
                    <td>{paraRole.descString}</td>
                    <td>
                      {paraRole.imageBlob ? (
                        <div>
                          <a onClick={openFile(paraRole.imageBlobContentType, paraRole.imageBlob)}>
                            <img src={`data:${paraRole.imageBlobContentType};base64,${paraRole.imageBlob}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                          <span>
                            {paraRole.imageBlobContentType}, {byteSize(paraRole.imageBlob)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{paraRole.imageBlobName}</td>
                    <td>{paraRole.usingFlag ? 'true' : 'false'}</td>
                    <td>{paraRole.remarks}</td>
                    <td>
                      {paraRole.createdUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraRole.createdUserId}`}>{paraRole.createdUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraRole.modifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraRole.modifiedUserId}`}>{paraRole.modifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraRole.verifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraRole.verifiedUserId}`}>{paraRole.verifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraRole.parentName ? <Link to={`para-role-sdm-suffix/${paraRole.parentId}`}>{paraRole.parentName}</Link> : ''}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${paraRole.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${paraRole.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${paraRole.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ paraRole }: IRootState) => ({
  paraRoleList: paraRole.entities,
  totalItems: paraRole.totalItems,
  links: paraRole.links,
  entity: paraRole.entity,
  updateSuccess: paraRole.updateSuccess
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
)(ParaRoleSdmSuffix);

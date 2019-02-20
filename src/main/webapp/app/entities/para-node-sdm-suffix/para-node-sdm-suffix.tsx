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
import { getSearchEntities, getEntities, reset } from './para-node-sdm-suffix.reducer';
import { IParaNodeSdmSuffix } from 'app/shared/model/para-node-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IParaNodeSdmSuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IParaNodeSdmSuffixState extends IPaginationBaseState {
  search: string;
}

export class ParaNodeSdmSuffix extends React.Component<IParaNodeSdmSuffixProps, IParaNodeSdmSuffixState> {
  state: IParaNodeSdmSuffixState = {
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
    const { paraNodeList, match } = this.props;
    return (
      <div>
        <h2 id="para-node-sdm-suffix-heading">
          <Translate contentKey="kmsApp.paraNode.home.title">Para Nodes</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="kmsApp.paraNode.home.createLabel">Create new Para Node</Translate>
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
                    placeholder={translate('kmsApp.paraNode.home.search')}
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
                    <Translate contentKey="kmsApp.paraNode.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('link')}>
                    <Translate contentKey="kmsApp.paraNode.link">Link</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('serialNumber')}>
                    <Translate contentKey="kmsApp.paraNode.serialNumber">Serial Number</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('sortString')}>
                    <Translate contentKey="kmsApp.paraNode.sortString">Sort String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('descString')}>
                    <Translate contentKey="kmsApp.paraNode.descString">Desc String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlob')}>
                    <Translate contentKey="kmsApp.paraNode.imageBlob">Image Blob</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlobName')}>
                    <Translate contentKey="kmsApp.paraNode.imageBlobName">Image Blob Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('usingFlag')}>
                    <Translate contentKey="kmsApp.paraNode.usingFlag">Using Flag</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('remarks')}>
                    <Translate contentKey="kmsApp.paraNode.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraNode.createdUser">Created User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraNode.modifiedUser">Modified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraNode.verifiedUser">Verified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.paraNode.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {paraNodeList.map((paraNode, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${paraNode.id}`} color="link" size="sm">
                        {paraNode.id}
                      </Button>
                    </td>
                    <td>{paraNode.name}</td>
                    <td>{paraNode.link}</td>
                    <td>{paraNode.serialNumber}</td>
                    <td>{paraNode.sortString}</td>
                    <td>{paraNode.descString}</td>
                    <td>
                      {paraNode.imageBlob ? (
                        <div>
                          <a onClick={openFile(paraNode.imageBlobContentType, paraNode.imageBlob)}>
                            <img src={`data:${paraNode.imageBlobContentType};base64,${paraNode.imageBlob}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                          <span>
                            {paraNode.imageBlobContentType}, {byteSize(paraNode.imageBlob)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{paraNode.imageBlobName}</td>
                    <td>{paraNode.usingFlag ? 'true' : 'false'}</td>
                    <td>{paraNode.remarks}</td>
                    <td>
                      {paraNode.createdUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraNode.createdUserId}`}>{paraNode.createdUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraNode.modifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraNode.modifiedUserId}`}>{paraNode.modifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraNode.verifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${paraNode.verifiedUserId}`}>{paraNode.verifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {paraNode.parentName ? <Link to={`para-node-sdm-suffix/${paraNode.parentId}`}>{paraNode.parentName}</Link> : ''}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${paraNode.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${paraNode.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${paraNode.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ paraNode }: IRootState) => ({
  paraNodeList: paraNode.entities,
  totalItems: paraNode.totalItems,
  links: paraNode.links,
  entity: paraNode.entity,
  updateSuccess: paraNode.updateSuccess
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
)(ParaNodeSdmSuffix);

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
import { getSearchEntities, getEntities, reset } from './kms-info-sdm-suffix.reducer';
import { IKmsInfoSdmSuffix } from 'app/shared/model/kms-info-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IKmsInfoSdmSuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface IKmsInfoSdmSuffixState extends IPaginationBaseState {
  search: string;
}

export class KmsInfoSdmSuffix extends React.Component<IKmsInfoSdmSuffixProps, IKmsInfoSdmSuffixState> {
  state: IKmsInfoSdmSuffixState = {
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
    const { kmsInfoList, match } = this.props;
    return (
      <div>
        <h2 id="kms-info-sdm-suffix-heading">
          <Translate contentKey="kmsApp.kmsInfo.home.title">Kms Infos</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="kmsApp.kmsInfo.home.createLabel">Create new Kms Info</Translate>
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
                    placeholder={translate('kmsApp.kmsInfo.home.search')}
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
                    <Translate contentKey="kmsApp.kmsInfo.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('serialNumber')}>
                    <Translate contentKey="kmsApp.kmsInfo.serialNumber">Serial Number</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('sortString')}>
                    <Translate contentKey="kmsApp.kmsInfo.sortString">Sort String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('descString')}>
                    <Translate contentKey="kmsApp.kmsInfo.descString">Desc String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('answer')}>
                    <Translate contentKey="kmsApp.kmsInfo.answer">Answer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('usingFlag')}>
                    <Translate contentKey="kmsApp.kmsInfo.usingFlag">Using Flag</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('versionNumber')}>
                    <Translate contentKey="kmsApp.kmsInfo.versionNumber">Version Number</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('remarks')}>
                    <Translate contentKey="kmsApp.kmsInfo.remarks">Remarks</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('attachmentPath')}>
                    <Translate contentKey="kmsApp.kmsInfo.attachmentPath">Attachment Path</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('attachmentBlob')}>
                    <Translate contentKey="kmsApp.kmsInfo.attachmentBlob">Attachment Blob</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('attachmentName')}>
                    <Translate contentKey="kmsApp.kmsInfo.attachmentName">Attachment Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('textBlob')}>
                    <Translate contentKey="kmsApp.kmsInfo.textBlob">Text Blob</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlob')}>
                    <Translate contentKey="kmsApp.kmsInfo.imageBlob">Image Blob</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('imageBlobName')}>
                    <Translate contentKey="kmsApp.kmsInfo.imageBlobName">Image Blob Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validType')}>
                    <Translate contentKey="kmsApp.kmsInfo.validType">Valid Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validBegin')}>
                    <Translate contentKey="kmsApp.kmsInfo.validBegin">Valid Begin</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('validEnd')}>
                    <Translate contentKey="kmsApp.kmsInfo.validEnd">Valid End</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('createTime')}>
                    <Translate contentKey="kmsApp.kmsInfo.createTime">Create Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('modifyTime')}>
                    <Translate contentKey="kmsApp.kmsInfo.modifyTime">Modify Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('verifyTime')}>
                    <Translate contentKey="kmsApp.kmsInfo.verifyTime">Verify Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('publishedTime')}>
                    <Translate contentKey="kmsApp.kmsInfo.publishedTime">Published Time</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('verifyNeed')}>
                    <Translate contentKey="kmsApp.kmsInfo.verifyNeed">Verify Need</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('markAsVerified')}>
                    <Translate contentKey="kmsApp.kmsInfo.markAsVerified">Mark As Verified</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('verifyResult')}>
                    <Translate contentKey="kmsApp.kmsInfo.verifyResult">Verify Result</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('verifyOpinion')}>
                    <Translate contentKey="kmsApp.kmsInfo.verifyOpinion">Verify Opinion</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('viewCount')}>
                    <Translate contentKey="kmsApp.kmsInfo.viewCount">View Count</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('viewPermission')}>
                    <Translate contentKey="kmsApp.kmsInfo.viewPermission">View Permission</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('viewPermPerson')}>
                    <Translate contentKey="kmsApp.kmsInfo.viewPermPerson">View Perm Person</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('paraSourceString')}>
                    <Translate contentKey="kmsApp.kmsInfo.paraSourceString">Para Source String</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.verifyRec">Verify Rec</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.paraType">Para Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.paraClass">Para Class</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.paraCat">Para Cat</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.paraState">Para State</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.paraSource">Para Source</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.paraAttr">Para Attr</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.paraProp">Para Prop</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.createdUser">Created User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.createdDepBy">Created Dep By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.ownerBy">Owner By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.ownerDepBy">Owner Dep By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.modifiedUser">Modified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.modifiedUserDep">Modified User Dep</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.verifiedUser">Verified User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.verifiedDepBy">Verified Dep By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.publishedBy">Published By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.publishedDepBy">Published Dep By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="kmsApp.kmsInfo.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {kmsInfoList.map((kmsInfo, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${kmsInfo.id}`} color="link" size="sm">
                        {kmsInfo.id}
                      </Button>
                    </td>
                    <td>{kmsInfo.name}</td>
                    <td>{kmsInfo.serialNumber}</td>
                    <td>{kmsInfo.sortString}</td>
                    <td>{kmsInfo.descString}</td>
                    <td>{kmsInfo.answer}</td>
                    <td>{kmsInfo.usingFlag ? 'true' : 'false'}</td>
                    <td>{kmsInfo.versionNumber}</td>
                    <td>{kmsInfo.remarks}</td>
                    <td>{kmsInfo.attachmentPath}</td>
                    <td>
                      {kmsInfo.attachmentBlob ? (
                        <div>
                          <a onClick={openFile(kmsInfo.attachmentBlobContentType, kmsInfo.attachmentBlob)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                          <span>
                            {kmsInfo.attachmentBlobContentType}, {byteSize(kmsInfo.attachmentBlob)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{kmsInfo.attachmentName}</td>
                    <td>{kmsInfo.textBlob}</td>
                    <td>
                      {kmsInfo.imageBlob ? (
                        <div>
                          <a onClick={openFile(kmsInfo.imageBlobContentType, kmsInfo.imageBlob)}>
                            <img src={`data:${kmsInfo.imageBlobContentType};base64,${kmsInfo.imageBlob}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                          <span>
                            {kmsInfo.imageBlobContentType}, {byteSize(kmsInfo.imageBlob)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{kmsInfo.imageBlobName}</td>
                    <td>
                      <Translate contentKey={`kmsApp.ValidType.${kmsInfo.validType}`} />
                    </td>
                    <td>
                      <TextFormat type="date" value={kmsInfo.validBegin} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={kmsInfo.validEnd} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={kmsInfo.createTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={kmsInfo.modifyTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={kmsInfo.verifyTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={kmsInfo.publishedTime} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{kmsInfo.verifyNeed ? 'true' : 'false'}</td>
                    <td>{kmsInfo.markAsVerified ? 'true' : 'false'}</td>
                    <td>{kmsInfo.verifyResult ? 'true' : 'false'}</td>
                    <td>{kmsInfo.verifyOpinion}</td>
                    <td>{kmsInfo.viewCount}</td>
                    <td>
                      <Translate contentKey={`kmsApp.ViewPermission.${kmsInfo.viewPermission}`} />
                    </td>
                    <td>{kmsInfo.viewPermPerson}</td>
                    <td>{kmsInfo.paraSourceString}</td>
                    <td>
                      {kmsInfo.verifyRecName ? (
                        <Link to={`verify-rec-sdm-suffix/${kmsInfo.verifyRecId}`}>{kmsInfo.verifyRecName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.paraTypeName ? <Link to={`para-type-sdm-suffix/${kmsInfo.paraTypeId}`}>{kmsInfo.paraTypeName}</Link> : ''}
                    </td>
                    <td>
                      {kmsInfo.paraClassName ? (
                        <Link to={`para-class-sdm-suffix/${kmsInfo.paraClassId}`}>{kmsInfo.paraClassName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{kmsInfo.paraCatName ? <Link to={`para-cat-sdm-suffix/${kmsInfo.paraCatId}`}>{kmsInfo.paraCatName}</Link> : ''}</td>
                    <td>
                      {kmsInfo.paraStateName ? (
                        <Link to={`para-state-sdm-suffix/${kmsInfo.paraStateId}`}>{kmsInfo.paraStateName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.paraSourceName ? (
                        <Link to={`para-source-sdm-suffix/${kmsInfo.paraSourceId}`}>{kmsInfo.paraSourceName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.paraAttrName ? <Link to={`para-attr-sdm-suffix/${kmsInfo.paraAttrId}`}>{kmsInfo.paraAttrName}</Link> : ''}
                    </td>
                    <td>
                      {kmsInfo.paraPropName ? <Link to={`para-prop-sdm-suffix/${kmsInfo.paraPropId}`}>{kmsInfo.paraPropName}</Link> : ''}
                    </td>
                    <td>
                      {kmsInfo.createdUserName ? (
                        <Link to={`para-user-sdm-suffix/${kmsInfo.createdUserId}`}>{kmsInfo.createdUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.createdDepByName ? (
                        <Link to={`para-dep-sdm-suffix/${kmsInfo.createdDepById}`}>{kmsInfo.createdDepByName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.ownerByName ? <Link to={`para-user-sdm-suffix/${kmsInfo.ownerById}`}>{kmsInfo.ownerByName}</Link> : ''}
                    </td>
                    <td>
                      {kmsInfo.ownerDepByName ? (
                        <Link to={`para-dep-sdm-suffix/${kmsInfo.ownerDepById}`}>{kmsInfo.ownerDepByName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.modifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${kmsInfo.modifiedUserId}`}>{kmsInfo.modifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.modifiedUserDepName ? (
                        <Link to={`para-dep-sdm-suffix/${kmsInfo.modifiedUserDepId}`}>{kmsInfo.modifiedUserDepName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.verifiedUserName ? (
                        <Link to={`para-user-sdm-suffix/${kmsInfo.verifiedUserId}`}>{kmsInfo.verifiedUserName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.verifiedDepByName ? (
                        <Link to={`para-dep-sdm-suffix/${kmsInfo.verifiedDepById}`}>{kmsInfo.verifiedDepByName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.publishedByName ? (
                        <Link to={`para-user-sdm-suffix/${kmsInfo.publishedById}`}>{kmsInfo.publishedByName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {kmsInfo.publishedDepByName ? (
                        <Link to={`para-dep-sdm-suffix/${kmsInfo.publishedDepById}`}>{kmsInfo.publishedDepByName}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{kmsInfo.parentName ? <Link to={`kms-info-sdm-suffix/${kmsInfo.parentId}`}>{kmsInfo.parentName}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${kmsInfo.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${kmsInfo.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${kmsInfo.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ kmsInfo }: IRootState) => ({
  kmsInfoList: kmsInfo.entities,
  totalItems: kmsInfo.totalItems,
  links: kmsInfo.links,
  entity: kmsInfo.entity,
  updateSuccess: kmsInfo.updateSuccess
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
)(KmsInfoSdmSuffix);

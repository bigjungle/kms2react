import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './kms-info-sdm-suffix.reducer';
import { IKmsInfoSdmSuffix } from 'app/shared/model/kms-info-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IKmsInfoSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class KmsInfoSdmSuffixDetail extends React.Component<IKmsInfoSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { kmsInfoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.kmsInfo.detail.title">KmsInfo</Translate> [<b>{kmsInfoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.kmsInfo.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.kmsInfo.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.name}</dd>
            <dt>
              <span id="serialNumber">
                <Translate contentKey="kmsApp.kmsInfo.serialNumber">Serial Number</Translate>
              </span>
              <UncontrolledTooltip target="serialNumber">
                <Translate contentKey="kmsApp.kmsInfo.help.serialNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.serialNumber}</dd>
            <dt>
              <span id="sortString">
                <Translate contentKey="kmsApp.kmsInfo.sortString">Sort String</Translate>
              </span>
              <UncontrolledTooltip target="sortString">
                <Translate contentKey="kmsApp.kmsInfo.help.sortString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.sortString}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.kmsInfo.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.kmsInfo.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.descString}</dd>
            <dt>
              <span id="answer">
                <Translate contentKey="kmsApp.kmsInfo.answer">Answer</Translate>
              </span>
              <UncontrolledTooltip target="answer">
                <Translate contentKey="kmsApp.kmsInfo.help.answer" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.answer}</dd>
            <dt>
              <span id="usingFlag">
                <Translate contentKey="kmsApp.kmsInfo.usingFlag">Using Flag</Translate>
              </span>
              <UncontrolledTooltip target="usingFlag">
                <Translate contentKey="kmsApp.kmsInfo.help.usingFlag" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.usingFlag ? 'true' : 'false'}</dd>
            <dt>
              <span id="versionNumber">
                <Translate contentKey="kmsApp.kmsInfo.versionNumber">Version Number</Translate>
              </span>
              <UncontrolledTooltip target="versionNumber">
                <Translate contentKey="kmsApp.kmsInfo.help.versionNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.versionNumber}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.kmsInfo.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.kmsInfo.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.remarks}</dd>
            <dt>
              <span id="attachmentPath">
                <Translate contentKey="kmsApp.kmsInfo.attachmentPath">Attachment Path</Translate>
              </span>
              <UncontrolledTooltip target="attachmentPath">
                <Translate contentKey="kmsApp.kmsInfo.help.attachmentPath" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.attachmentPath}</dd>
            <dt>
              <span id="attachmentBlob">
                <Translate contentKey="kmsApp.kmsInfo.attachmentBlob">Attachment Blob</Translate>
              </span>
              <UncontrolledTooltip target="attachmentBlob">
                <Translate contentKey="kmsApp.kmsInfo.help.attachmentBlob" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              {kmsInfoEntity.attachmentBlob ? (
                <div>
                  <a onClick={openFile(kmsInfoEntity.attachmentBlobContentType, kmsInfoEntity.attachmentBlob)}>
                    <Translate contentKey="entity.action.open">Open</Translate>
                    &nbsp;
                  </a>
                  <span>
                    {kmsInfoEntity.attachmentBlobContentType}, {byteSize(kmsInfoEntity.attachmentBlob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="attachmentName">
                <Translate contentKey="kmsApp.kmsInfo.attachmentName">Attachment Name</Translate>
              </span>
              <UncontrolledTooltip target="attachmentName">
                <Translate contentKey="kmsApp.kmsInfo.help.attachmentName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.attachmentName}</dd>
            <dt>
              <span id="textBlob">
                <Translate contentKey="kmsApp.kmsInfo.textBlob">Text Blob</Translate>
              </span>
              <UncontrolledTooltip target="textBlob">
                <Translate contentKey="kmsApp.kmsInfo.help.textBlob" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.textBlob}</dd>
            <dt>
              <span id="imageBlob">
                <Translate contentKey="kmsApp.kmsInfo.imageBlob">Image Blob</Translate>
              </span>
              <UncontrolledTooltip target="imageBlob">
                <Translate contentKey="kmsApp.kmsInfo.help.imageBlob" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              {kmsInfoEntity.imageBlob ? (
                <div>
                  <a onClick={openFile(kmsInfoEntity.imageBlobContentType, kmsInfoEntity.imageBlob)}>
                    <img
                      src={`data:${kmsInfoEntity.imageBlobContentType};base64,${kmsInfoEntity.imageBlob}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {kmsInfoEntity.imageBlobContentType}, {byteSize(kmsInfoEntity.imageBlob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="imageBlobName">
                <Translate contentKey="kmsApp.kmsInfo.imageBlobName">Image Blob Name</Translate>
              </span>
              <UncontrolledTooltip target="imageBlobName">
                <Translate contentKey="kmsApp.kmsInfo.help.imageBlobName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.imageBlobName}</dd>
            <dt>
              <span id="validType">
                <Translate contentKey="kmsApp.kmsInfo.validType">Valid Type</Translate>
              </span>
              <UncontrolledTooltip target="validType">
                <Translate contentKey="kmsApp.kmsInfo.help.validType" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.validType}</dd>
            <dt>
              <span id="validBegin">
                <Translate contentKey="kmsApp.kmsInfo.validBegin">Valid Begin</Translate>
              </span>
              <UncontrolledTooltip target="validBegin">
                <Translate contentKey="kmsApp.kmsInfo.help.validBegin" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={kmsInfoEntity.validBegin} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="validEnd">
                <Translate contentKey="kmsApp.kmsInfo.validEnd">Valid End</Translate>
              </span>
              <UncontrolledTooltip target="validEnd">
                <Translate contentKey="kmsApp.kmsInfo.help.validEnd" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={kmsInfoEntity.validEnd} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createTime">
                <Translate contentKey="kmsApp.kmsInfo.createTime">Create Time</Translate>
              </span>
              <UncontrolledTooltip target="createTime">
                <Translate contentKey="kmsApp.kmsInfo.help.createTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={kmsInfoEntity.createTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="modifyTime">
                <Translate contentKey="kmsApp.kmsInfo.modifyTime">Modify Time</Translate>
              </span>
              <UncontrolledTooltip target="modifyTime">
                <Translate contentKey="kmsApp.kmsInfo.help.modifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={kmsInfoEntity.modifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="verifyTime">
                <Translate contentKey="kmsApp.kmsInfo.verifyTime">Verify Time</Translate>
              </span>
              <UncontrolledTooltip target="verifyTime">
                <Translate contentKey="kmsApp.kmsInfo.help.verifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={kmsInfoEntity.verifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="publishedTime">
                <Translate contentKey="kmsApp.kmsInfo.publishedTime">Published Time</Translate>
              </span>
              <UncontrolledTooltip target="publishedTime">
                <Translate contentKey="kmsApp.kmsInfo.help.publishedTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={kmsInfoEntity.publishedTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="verifyNeed">
                <Translate contentKey="kmsApp.kmsInfo.verifyNeed">Verify Need</Translate>
              </span>
              <UncontrolledTooltip target="verifyNeed">
                <Translate contentKey="kmsApp.kmsInfo.help.verifyNeed" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.verifyNeed ? 'true' : 'false'}</dd>
            <dt>
              <span id="markAsVerified">
                <Translate contentKey="kmsApp.kmsInfo.markAsVerified">Mark As Verified</Translate>
              </span>
              <UncontrolledTooltip target="markAsVerified">
                <Translate contentKey="kmsApp.kmsInfo.help.markAsVerified" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.markAsVerified ? 'true' : 'false'}</dd>
            <dt>
              <span id="verifyResult">
                <Translate contentKey="kmsApp.kmsInfo.verifyResult">Verify Result</Translate>
              </span>
              <UncontrolledTooltip target="verifyResult">
                <Translate contentKey="kmsApp.kmsInfo.help.verifyResult" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.verifyResult ? 'true' : 'false'}</dd>
            <dt>
              <span id="verifyOpinion">
                <Translate contentKey="kmsApp.kmsInfo.verifyOpinion">Verify Opinion</Translate>
              </span>
              <UncontrolledTooltip target="verifyOpinion">
                <Translate contentKey="kmsApp.kmsInfo.help.verifyOpinion" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.verifyOpinion}</dd>
            <dt>
              <span id="viewCount">
                <Translate contentKey="kmsApp.kmsInfo.viewCount">View Count</Translate>
              </span>
              <UncontrolledTooltip target="viewCount">
                <Translate contentKey="kmsApp.kmsInfo.help.viewCount" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.viewCount}</dd>
            <dt>
              <span id="viewPermission">
                <Translate contentKey="kmsApp.kmsInfo.viewPermission">View Permission</Translate>
              </span>
              <UncontrolledTooltip target="viewPermission">
                <Translate contentKey="kmsApp.kmsInfo.help.viewPermission" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.viewPermission}</dd>
            <dt>
              <span id="viewPermPerson">
                <Translate contentKey="kmsApp.kmsInfo.viewPermPerson">View Perm Person</Translate>
              </span>
              <UncontrolledTooltip target="viewPermPerson">
                <Translate contentKey="kmsApp.kmsInfo.help.viewPermPerson" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.viewPermPerson}</dd>
            <dt>
              <span id="paraSourceString">
                <Translate contentKey="kmsApp.kmsInfo.paraSourceString">Para Source String</Translate>
              </span>
              <UncontrolledTooltip target="paraSourceString">
                <Translate contentKey="kmsApp.kmsInfo.help.paraSourceString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{kmsInfoEntity.paraSourceString}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.verifyRec">Verify Rec</Translate>
            </dt>
            <dd>{kmsInfoEntity.verifyRecName ? kmsInfoEntity.verifyRecName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.paraType">Para Type</Translate>
            </dt>
            <dd>{kmsInfoEntity.paraTypeName ? kmsInfoEntity.paraTypeName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.paraClass">Para Class</Translate>
            </dt>
            <dd>{kmsInfoEntity.paraClassName ? kmsInfoEntity.paraClassName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.paraCat">Para Cat</Translate>
            </dt>
            <dd>{kmsInfoEntity.paraCatName ? kmsInfoEntity.paraCatName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.paraState">Para State</Translate>
            </dt>
            <dd>{kmsInfoEntity.paraStateName ? kmsInfoEntity.paraStateName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.paraSource">Para Source</Translate>
            </dt>
            <dd>{kmsInfoEntity.paraSourceName ? kmsInfoEntity.paraSourceName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.paraAttr">Para Attr</Translate>
            </dt>
            <dd>{kmsInfoEntity.paraAttrName ? kmsInfoEntity.paraAttrName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.paraProp">Para Prop</Translate>
            </dt>
            <dd>{kmsInfoEntity.paraPropName ? kmsInfoEntity.paraPropName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.createdUser">Created User</Translate>
            </dt>
            <dd>{kmsInfoEntity.createdUserName ? kmsInfoEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.createdDepBy">Created Dep By</Translate>
            </dt>
            <dd>{kmsInfoEntity.createdDepByName ? kmsInfoEntity.createdDepByName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.ownerBy">Owner By</Translate>
            </dt>
            <dd>{kmsInfoEntity.ownerByName ? kmsInfoEntity.ownerByName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.ownerDepBy">Owner Dep By</Translate>
            </dt>
            <dd>{kmsInfoEntity.ownerDepByName ? kmsInfoEntity.ownerDepByName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{kmsInfoEntity.modifiedUserName ? kmsInfoEntity.modifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.modifiedUserDep">Modified User Dep</Translate>
            </dt>
            <dd>{kmsInfoEntity.modifiedUserDepName ? kmsInfoEntity.modifiedUserDepName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.verifiedUser">Verified User</Translate>
            </dt>
            <dd>{kmsInfoEntity.verifiedUserName ? kmsInfoEntity.verifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.verifiedDepBy">Verified Dep By</Translate>
            </dt>
            <dd>{kmsInfoEntity.verifiedDepByName ? kmsInfoEntity.verifiedDepByName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.publishedBy">Published By</Translate>
            </dt>
            <dd>{kmsInfoEntity.publishedByName ? kmsInfoEntity.publishedByName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.publishedDepBy">Published Dep By</Translate>
            </dt>
            <dd>{kmsInfoEntity.publishedDepByName ? kmsInfoEntity.publishedDepByName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.parent">Parent</Translate>
            </dt>
            <dd>{kmsInfoEntity.parentName ? kmsInfoEntity.parentName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.kmsInfo.paraOther">Para Other</Translate>
            </dt>
            <dd>
              {kmsInfoEntity.paraOthers
                ? kmsInfoEntity.paraOthers.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === kmsInfoEntity.paraOthers.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/kms-info-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/kms-info-sdm-suffix/${kmsInfoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ kmsInfo }: IRootState) => ({
  kmsInfoEntity: kmsInfo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(KmsInfoSdmSuffixDetail);

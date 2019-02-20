import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVerifyRecSdmSuffix } from 'app/shared/model/verify-rec-sdm-suffix.model';
import { getEntities as getVerifyRecs } from 'app/entities/verify-rec-sdm-suffix/verify-rec-sdm-suffix.reducer';
import { IParaTypeSdmSuffix } from 'app/shared/model/para-type-sdm-suffix.model';
import { getEntities as getParaTypes } from 'app/entities/para-type-sdm-suffix/para-type-sdm-suffix.reducer';
import { IParaClassSdmSuffix } from 'app/shared/model/para-class-sdm-suffix.model';
import { getEntities as getParaClasses } from 'app/entities/para-class-sdm-suffix/para-class-sdm-suffix.reducer';
import { IParaCatSdmSuffix } from 'app/shared/model/para-cat-sdm-suffix.model';
import { getEntities as getParaCats } from 'app/entities/para-cat-sdm-suffix/para-cat-sdm-suffix.reducer';
import { IParaStateSdmSuffix } from 'app/shared/model/para-state-sdm-suffix.model';
import { getEntities as getParaStates } from 'app/entities/para-state-sdm-suffix/para-state-sdm-suffix.reducer';
import { IParaSourceSdmSuffix } from 'app/shared/model/para-source-sdm-suffix.model';
import { getEntities as getParaSources } from 'app/entities/para-source-sdm-suffix/para-source-sdm-suffix.reducer';
import { IParaAttrSdmSuffix } from 'app/shared/model/para-attr-sdm-suffix.model';
import { getEntities as getParaAttrs } from 'app/entities/para-attr-sdm-suffix/para-attr-sdm-suffix.reducer';
import { IParaPropSdmSuffix } from 'app/shared/model/para-prop-sdm-suffix.model';
import { getEntities as getParaProps } from 'app/entities/para-prop-sdm-suffix/para-prop-sdm-suffix.reducer';
import { IParaUserSdmSuffix } from 'app/shared/model/para-user-sdm-suffix.model';
import { getEntities as getParaUsers } from 'app/entities/para-user-sdm-suffix/para-user-sdm-suffix.reducer';
import { IParaDepSdmSuffix } from 'app/shared/model/para-dep-sdm-suffix.model';
import { getEntities as getParaDeps } from 'app/entities/para-dep-sdm-suffix/para-dep-sdm-suffix.reducer';
import { getEntities as getKmsInfos } from 'app/entities/kms-info-sdm-suffix/kms-info-sdm-suffix.reducer';
import { IParaOtherSdmSuffix } from 'app/shared/model/para-other-sdm-suffix.model';
import { getEntities as getParaOthers } from 'app/entities/para-other-sdm-suffix/para-other-sdm-suffix.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './kms-info-sdm-suffix.reducer';
import { IKmsInfoSdmSuffix } from 'app/shared/model/kms-info-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IKmsInfoSdmSuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IKmsInfoSdmSuffixUpdateState {
  isNew: boolean;
  idsparaOther: any[];
  verifyRecId: string;
  paraTypeId: string;
  paraClassId: string;
  paraCatId: string;
  paraStateId: string;
  paraSourceId: string;
  paraAttrId: string;
  paraPropId: string;
  createdUserId: string;
  ownerById: string;
  modifiedUserId: string;
  verifiedUserId: string;
  publishedById: string;
  createdDepById: string;
  ownerDepById: string;
  modifiedUserDepId: string;
  verifiedDepById: string;
  publishedDepById: string;
  parentId: string;
}

export class KmsInfoSdmSuffixUpdate extends React.Component<IKmsInfoSdmSuffixUpdateProps, IKmsInfoSdmSuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsparaOther: [],
      verifyRecId: '0',
      paraTypeId: '0',
      paraClassId: '0',
      paraCatId: '0',
      paraStateId: '0',
      paraSourceId: '0',
      paraAttrId: '0',
      paraPropId: '0',
      createdUserId: '0',
      ownerById: '0',
      modifiedUserId: '0',
      verifiedUserId: '0',
      publishedById: '0',
      createdDepById: '0',
      ownerDepById: '0',
      modifiedUserDepId: '0',
      verifiedDepById: '0',
      publishedDepById: '0',
      parentId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getVerifyRecs();
    this.props.getParaTypes();
    this.props.getParaClasses();
    this.props.getParaCats();
    this.props.getParaStates();
    this.props.getParaSources();
    this.props.getParaAttrs();
    this.props.getParaProps();
    this.props.getParaUsers();
    this.props.getParaDeps();
    this.props.getKmsInfos();
    this.props.getParaOthers();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    values.validBegin = convertDateTimeToServer(values.validBegin);
    values.validEnd = convertDateTimeToServer(values.validEnd);
    values.createTime = convertDateTimeToServer(values.createTime);
    values.modifyTime = convertDateTimeToServer(values.modifyTime);
    values.verifyTime = convertDateTimeToServer(values.verifyTime);
    values.publishedTime = convertDateTimeToServer(values.publishedTime);

    if (errors.length === 0) {
      const { kmsInfoEntity } = this.props;
      const entity = {
        ...kmsInfoEntity,
        ...values,
        paraOthers: mapIdList(values.paraOthers)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/kms-info-sdm-suffix');
  };

  render() {
    const {
      kmsInfoEntity,
      verifyRecs,
      paraTypes,
      paraClasses,
      paraCats,
      paraStates,
      paraSources,
      paraAttrs,
      paraProps,
      paraUsers,
      paraDeps,
      kmsInfos,
      paraOthers,
      loading,
      updating
    } = this.props;
    const { isNew } = this.state;

    const { attachmentBlob, attachmentBlobContentType, textBlob, imageBlob, imageBlobContentType } = kmsInfoEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="kmsApp.kmsInfo.home.createOrEditLabel">
              <Translate contentKey="kmsApp.kmsInfo.home.createOrEditLabel">Create or edit a KmsInfo</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : kmsInfoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="kms-info-sdm-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="kmsApp.kmsInfo.name">Name</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-name"
                    type="text"
                    name="name"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="nameLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.name" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="serialNumberLabel" for="serialNumber">
                    <Translate contentKey="kmsApp.kmsInfo.serialNumber">Serial Number</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-serialNumber"
                    type="text"
                    name="serialNumber"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="serialNumberLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.serialNumber" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="sortStringLabel" for="sortString">
                    <Translate contentKey="kmsApp.kmsInfo.sortString">Sort String</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-sortString"
                    type="text"
                    name="sortString"
                    validate={{
                      maxLength: { value: 10, errorMessage: translate('entity.validation.maxlength', { max: 10 }) }
                    }}
                  />
                  <UncontrolledTooltip target="sortStringLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.sortString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="descStringLabel" for="descString">
                    <Translate contentKey="kmsApp.kmsInfo.descString">Desc String</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-descString"
                    type="text"
                    name="descString"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="descStringLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.descString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="answerLabel" for="answer">
                    <Translate contentKey="kmsApp.kmsInfo.answer">Answer</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-answer"
                    type="text"
                    name="answer"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="answerLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.answer" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="usingFlagLabel" check>
                    <AvInput id="kms-info-sdm-suffix-usingFlag" type="checkbox" className="form-control" name="usingFlag" />
                    <Translate contentKey="kmsApp.kmsInfo.usingFlag">Using Flag</Translate>
                  </Label>
                  <UncontrolledTooltip target="usingFlagLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.usingFlag" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="versionNumberLabel" for="versionNumber">
                    <Translate contentKey="kmsApp.kmsInfo.versionNumber">Version Number</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-versionNumber"
                    type="text"
                    name="versionNumber"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="versionNumberLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.versionNumber" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="remarks">
                    <Translate contentKey="kmsApp.kmsInfo.remarks">Remarks</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-remarks"
                    type="text"
                    name="remarks"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="remarksLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.remarks" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="attachmentPathLabel" for="attachmentPath">
                    <Translate contentKey="kmsApp.kmsInfo.attachmentPath">Attachment Path</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-attachmentPath"
                    type="text"
                    name="attachmentPath"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="attachmentPathLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.attachmentPath" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="attachmentBlobLabel" for="attachmentBlob">
                      <Translate contentKey="kmsApp.kmsInfo.attachmentBlob">Attachment Blob</Translate>
                    </Label>
                    <br />
                    {attachmentBlob ? (
                      <div>
                        <a onClick={openFile(attachmentBlobContentType, attachmentBlob)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {attachmentBlobContentType}, {byteSize(attachmentBlob)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('attachmentBlob')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_attachmentBlob" type="file" onChange={this.onBlobChange(false, 'attachmentBlob')} />
                    <AvInput type="hidden" name="attachmentBlob" value={attachmentBlob} />
                  </AvGroup>

                  <UncontrolledTooltip target="attachmentBlobLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.attachmentBlob" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="attachmentNameLabel" for="attachmentName">
                    <Translate contentKey="kmsApp.kmsInfo.attachmentName">Attachment Name</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-attachmentName"
                    type="text"
                    name="attachmentName"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="attachmentNameLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.attachmentName" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="textBlobLabel" for="textBlob">
                    <Translate contentKey="kmsApp.kmsInfo.textBlob">Text Blob</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-textBlob" type="textarea" name="textBlob" />
                  <UncontrolledTooltip target="textBlobLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.textBlob" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imageBlobLabel" for="imageBlob">
                      <Translate contentKey="kmsApp.kmsInfo.imageBlob">Image Blob</Translate>
                    </Label>
                    <br />
                    {imageBlob ? (
                      <div>
                        <a onClick={openFile(imageBlobContentType, imageBlob)}>
                          <img src={`data:${imageBlobContentType};base64,${imageBlob}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {imageBlobContentType}, {byteSize(imageBlob)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('imageBlob')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_imageBlob" type="file" onChange={this.onBlobChange(true, 'imageBlob')} accept="image/*" />
                    <AvInput type="hidden" name="imageBlob" value={imageBlob} />
                  </AvGroup>

                  <UncontrolledTooltip target="imageBlobLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.imageBlob" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="imageBlobNameLabel" for="imageBlobName">
                    <Translate contentKey="kmsApp.kmsInfo.imageBlobName">Image Blob Name</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-imageBlobName"
                    type="text"
                    name="imageBlobName"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="imageBlobNameLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.imageBlobName" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validTypeLabel">
                    <Translate contentKey="kmsApp.kmsInfo.validType">Valid Type</Translate>
                  </Label>
                  <AvInput
                    id="kms-info-sdm-suffix-validType"
                    type="select"
                    className="form-control"
                    name="validType"
                    value={(!isNew && kmsInfoEntity.validType) || 'LONG'}
                  >
                    <option value="LONG">
                      <Translate contentKey="kmsApp.ValidType.LONG" />
                    </option>
                    <option value="SCOPE">
                      <Translate contentKey="kmsApp.ValidType.SCOPE" />
                    </option>
                  </AvInput>
                  <UncontrolledTooltip target="validTypeLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.validType" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validBeginLabel" for="validBegin">
                    <Translate contentKey="kmsApp.kmsInfo.validBegin">Valid Begin</Translate>
                  </Label>
                  <AvInput
                    id="kms-info-sdm-suffix-validBegin"
                    type="datetime-local"
                    className="form-control"
                    name="validBegin"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.kmsInfoEntity.validBegin)}
                  />
                  <UncontrolledTooltip target="validBeginLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.validBegin" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validEndLabel" for="validEnd">
                    <Translate contentKey="kmsApp.kmsInfo.validEnd">Valid End</Translate>
                  </Label>
                  <AvInput
                    id="kms-info-sdm-suffix-validEnd"
                    type="datetime-local"
                    className="form-control"
                    name="validEnd"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.kmsInfoEntity.validEnd)}
                  />
                  <UncontrolledTooltip target="validEndLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.validEnd" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="createTimeLabel" for="createTime">
                    <Translate contentKey="kmsApp.kmsInfo.createTime">Create Time</Translate>
                  </Label>
                  <AvInput
                    id="kms-info-sdm-suffix-createTime"
                    type="datetime-local"
                    className="form-control"
                    name="createTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.kmsInfoEntity.createTime)}
                  />
                  <UncontrolledTooltip target="createTimeLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.createTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="modifyTimeLabel" for="modifyTime">
                    <Translate contentKey="kmsApp.kmsInfo.modifyTime">Modify Time</Translate>
                  </Label>
                  <AvInput
                    id="kms-info-sdm-suffix-modifyTime"
                    type="datetime-local"
                    className="form-control"
                    name="modifyTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.kmsInfoEntity.modifyTime)}
                  />
                  <UncontrolledTooltip target="modifyTimeLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.modifyTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="verifyTimeLabel" for="verifyTime">
                    <Translate contentKey="kmsApp.kmsInfo.verifyTime">Verify Time</Translate>
                  </Label>
                  <AvInput
                    id="kms-info-sdm-suffix-verifyTime"
                    type="datetime-local"
                    className="form-control"
                    name="verifyTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.kmsInfoEntity.verifyTime)}
                  />
                  <UncontrolledTooltip target="verifyTimeLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.verifyTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="publishedTimeLabel" for="publishedTime">
                    <Translate contentKey="kmsApp.kmsInfo.publishedTime">Published Time</Translate>
                  </Label>
                  <AvInput
                    id="kms-info-sdm-suffix-publishedTime"
                    type="datetime-local"
                    className="form-control"
                    name="publishedTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.kmsInfoEntity.publishedTime)}
                  />
                  <UncontrolledTooltip target="publishedTimeLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.publishedTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="verifyNeedLabel" check>
                    <AvInput id="kms-info-sdm-suffix-verifyNeed" type="checkbox" className="form-control" name="verifyNeed" />
                    <Translate contentKey="kmsApp.kmsInfo.verifyNeed">Verify Need</Translate>
                  </Label>
                  <UncontrolledTooltip target="verifyNeedLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.verifyNeed" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="markAsVerifiedLabel" check>
                    <AvInput id="kms-info-sdm-suffix-markAsVerified" type="checkbox" className="form-control" name="markAsVerified" />
                    <Translate contentKey="kmsApp.kmsInfo.markAsVerified">Mark As Verified</Translate>
                  </Label>
                  <UncontrolledTooltip target="markAsVerifiedLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.markAsVerified" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="verifyResultLabel" check>
                    <AvInput id="kms-info-sdm-suffix-verifyResult" type="checkbox" className="form-control" name="verifyResult" />
                    <Translate contentKey="kmsApp.kmsInfo.verifyResult">Verify Result</Translate>
                  </Label>
                  <UncontrolledTooltip target="verifyResultLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.verifyResult" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="verifyOpinionLabel" for="verifyOpinion">
                    <Translate contentKey="kmsApp.kmsInfo.verifyOpinion">Verify Opinion</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-verifyOpinion"
                    type="text"
                    name="verifyOpinion"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="verifyOpinionLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.verifyOpinion" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="viewCountLabel" for="viewCount">
                    <Translate contentKey="kmsApp.kmsInfo.viewCount">View Count</Translate>
                  </Label>
                  <AvField id="kms-info-sdm-suffix-viewCount" type="string" className="form-control" name="viewCount" />
                  <UncontrolledTooltip target="viewCountLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.viewCount" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="viewPermissionLabel">
                    <Translate contentKey="kmsApp.kmsInfo.viewPermission">View Permission</Translate>
                  </Label>
                  <AvInput
                    id="kms-info-sdm-suffix-viewPermission"
                    type="select"
                    className="form-control"
                    name="viewPermission"
                    value={(!isNew && kmsInfoEntity.viewPermission) || 'PRIVATEVIEW'}
                  >
                    <option value="PRIVATEVIEW">
                      <Translate contentKey="kmsApp.ViewPermission.PRIVATEVIEW" />
                    </option>
                    <option value="DEFAULTVIEW">
                      <Translate contentKey="kmsApp.ViewPermission.DEFAULTVIEW" />
                    </option>
                    <option value="PROTECTVIEW">
                      <Translate contentKey="kmsApp.ViewPermission.PROTECTVIEW" />
                    </option>
                    <option value="PUBLICVIEW">
                      <Translate contentKey="kmsApp.ViewPermission.PUBLICVIEW" />
                    </option>
                    <option value="CUSTOMVIEW">
                      <Translate contentKey="kmsApp.ViewPermission.CUSTOMVIEW" />
                    </option>
                  </AvInput>
                  <UncontrolledTooltip target="viewPermissionLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.viewPermission" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="viewPermPersonLabel" for="viewPermPerson">
                    <Translate contentKey="kmsApp.kmsInfo.viewPermPerson">View Perm Person</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-viewPermPerson"
                    type="text"
                    name="viewPermPerson"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="viewPermPersonLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.viewPermPerson" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="paraSourceStringLabel" for="paraSourceString">
                    <Translate contentKey="kmsApp.kmsInfo.paraSourceString">Para Source String</Translate>
                  </Label>
                  <AvField
                    id="kms-info-sdm-suffix-paraSourceString"
                    type="text"
                    name="paraSourceString"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="paraSourceStringLabel">
                    <Translate contentKey="kmsApp.kmsInfo.help.paraSourceString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label for="verifyRec.name">
                    <Translate contentKey="kmsApp.kmsInfo.verifyRec">Verify Rec</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-verifyRec" type="select" className="form-control" name="verifyRecId">
                    <option value="" key="0" />
                    {verifyRecs
                      ? verifyRecs.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="paraType.name">
                    <Translate contentKey="kmsApp.kmsInfo.paraType">Para Type</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-paraType" type="select" className="form-control" name="paraTypeId">
                    <option value="" key="0" />
                    {paraTypes
                      ? paraTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="paraClass.name">
                    <Translate contentKey="kmsApp.kmsInfo.paraClass">Para Class</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-paraClass" type="select" className="form-control" name="paraClassId">
                    <option value="" key="0" />
                    {paraClasses
                      ? paraClasses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="paraCat.name">
                    <Translate contentKey="kmsApp.kmsInfo.paraCat">Para Cat</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-paraCat" type="select" className="form-control" name="paraCatId">
                    <option value="" key="0" />
                    {paraCats
                      ? paraCats.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="paraState.name">
                    <Translate contentKey="kmsApp.kmsInfo.paraState">Para State</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-paraState" type="select" className="form-control" name="paraStateId">
                    <option value="" key="0" />
                    {paraStates
                      ? paraStates.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="paraSource.name">
                    <Translate contentKey="kmsApp.kmsInfo.paraSource">Para Source</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-paraSource" type="select" className="form-control" name="paraSourceId">
                    <option value="" key="0" />
                    {paraSources
                      ? paraSources.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="paraAttr.name">
                    <Translate contentKey="kmsApp.kmsInfo.paraAttr">Para Attr</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-paraAttr" type="select" className="form-control" name="paraAttrId">
                    <option value="" key="0" />
                    {paraAttrs
                      ? paraAttrs.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="paraProp.name">
                    <Translate contentKey="kmsApp.kmsInfo.paraProp">Para Prop</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-paraProp" type="select" className="form-control" name="paraPropId">
                    <option value="" key="0" />
                    {paraProps
                      ? paraProps.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="createdUser.name">
                    <Translate contentKey="kmsApp.kmsInfo.createdUser">Created User</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-createdUser" type="select" className="form-control" name="createdUserId">
                    <option value="" key="0" />
                    {paraUsers
                      ? paraUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="createdDepBy.name">
                    <Translate contentKey="kmsApp.kmsInfo.createdDepBy">Created Dep By</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-createdDepBy" type="select" className="form-control" name="createdDepById">
                    <option value="" key="0" />
                    {paraDeps
                      ? paraDeps.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="ownerBy.name">
                    <Translate contentKey="kmsApp.kmsInfo.ownerBy">Owner By</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-ownerBy" type="select" className="form-control" name="ownerById">
                    <option value="" key="0" />
                    {paraUsers
                      ? paraUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="ownerDepBy.name">
                    <Translate contentKey="kmsApp.kmsInfo.ownerDepBy">Owner Dep By</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-ownerDepBy" type="select" className="form-control" name="ownerDepById">
                    <option value="" key="0" />
                    {paraDeps
                      ? paraDeps.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="modifiedUser.name">
                    <Translate contentKey="kmsApp.kmsInfo.modifiedUser">Modified User</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-modifiedUser" type="select" className="form-control" name="modifiedUserId">
                    <option value="" key="0" />
                    {paraUsers
                      ? paraUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="modifiedUserDep.name">
                    <Translate contentKey="kmsApp.kmsInfo.modifiedUserDep">Modified User Dep</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-modifiedUserDep" type="select" className="form-control" name="modifiedUserDepId">
                    <option value="" key="0" />
                    {paraDeps
                      ? paraDeps.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="verifiedUser.name">
                    <Translate contentKey="kmsApp.kmsInfo.verifiedUser">Verified User</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-verifiedUser" type="select" className="form-control" name="verifiedUserId">
                    <option value="" key="0" />
                    {paraUsers
                      ? paraUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="verifiedDepBy.name">
                    <Translate contentKey="kmsApp.kmsInfo.verifiedDepBy">Verified Dep By</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-verifiedDepBy" type="select" className="form-control" name="verifiedDepById">
                    <option value="" key="0" />
                    {paraDeps
                      ? paraDeps.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="publishedBy.name">
                    <Translate contentKey="kmsApp.kmsInfo.publishedBy">Published By</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-publishedBy" type="select" className="form-control" name="publishedById">
                    <option value="" key="0" />
                    {paraUsers
                      ? paraUsers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="publishedDepBy.name">
                    <Translate contentKey="kmsApp.kmsInfo.publishedDepBy">Published Dep By</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-publishedDepBy" type="select" className="form-control" name="publishedDepById">
                    <option value="" key="0" />
                    {paraDeps
                      ? paraDeps.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="parent.name">
                    <Translate contentKey="kmsApp.kmsInfo.parent">Parent</Translate>
                  </Label>
                  <AvInput id="kms-info-sdm-suffix-parent" type="select" className="form-control" name="parentId">
                    <option value="" key="0" />
                    {kmsInfos
                      ? kmsInfos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="paraOthers">
                    <Translate contentKey="kmsApp.kmsInfo.paraOther">Para Other</Translate>
                  </Label>
                  <AvInput
                    id="kms-info-sdm-suffix-paraOther"
                    type="select"
                    multiple
                    className="form-control"
                    name="paraOthers"
                    value={kmsInfoEntity.paraOthers && kmsInfoEntity.paraOthers.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {paraOthers
                      ? paraOthers.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/kms-info-sdm-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  verifyRecs: storeState.verifyRec.entities,
  paraTypes: storeState.paraType.entities,
  paraClasses: storeState.paraClass.entities,
  paraCats: storeState.paraCat.entities,
  paraStates: storeState.paraState.entities,
  paraSources: storeState.paraSource.entities,
  paraAttrs: storeState.paraAttr.entities,
  paraProps: storeState.paraProp.entities,
  paraUsers: storeState.paraUser.entities,
  paraDeps: storeState.paraDep.entities,
  kmsInfos: storeState.kmsInfo.entities,
  paraOthers: storeState.paraOther.entities,
  kmsInfoEntity: storeState.kmsInfo.entity,
  loading: storeState.kmsInfo.loading,
  updating: storeState.kmsInfo.updating,
  updateSuccess: storeState.kmsInfo.updateSuccess
});

const mapDispatchToProps = {
  getVerifyRecs,
  getParaTypes,
  getParaClasses,
  getParaCats,
  getParaStates,
  getParaSources,
  getParaAttrs,
  getParaProps,
  getParaUsers,
  getParaDeps,
  getKmsInfos,
  getParaOthers,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(KmsInfoSdmSuffixUpdate);

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IParaUserSdmSuffix } from 'app/shared/model/para-user-sdm-suffix.model';
import { getEntities as getParaUsers } from 'app/entities/para-user-sdm-suffix/para-user-sdm-suffix.reducer';
import { IKmsInfoSdmSuffix } from 'app/shared/model/kms-info-sdm-suffix.model';
import { getEntities as getKmsInfos } from 'app/entities/kms-info-sdm-suffix/kms-info-sdm-suffix.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './para-other-sdm-suffix.reducer';
import { IParaOtherSdmSuffix } from 'app/shared/model/para-other-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IParaOtherSdmSuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IParaOtherSdmSuffixUpdateState {
  isNew: boolean;
  createdUserId: string;
  modifiedUserId: string;
  verifiedUserId: string;
  kmsInfoId: string;
}

export class ParaOtherSdmSuffixUpdate extends React.Component<IParaOtherSdmSuffixUpdateProps, IParaOtherSdmSuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdUserId: '0',
      modifiedUserId: '0',
      verifiedUserId: '0',
      kmsInfoId: '0',
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

    this.props.getParaUsers();
    this.props.getKmsInfos();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    values.paraValueIn = convertDateTimeToServer(values.paraValueIn);
    values.validBegin = convertDateTimeToServer(values.validBegin);
    values.validEnd = convertDateTimeToServer(values.validEnd);
    values.createTime = convertDateTimeToServer(values.createTime);
    values.modifyTime = convertDateTimeToServer(values.modifyTime);
    values.verifyTime = convertDateTimeToServer(values.verifyTime);

    if (errors.length === 0) {
      const { paraOtherEntity } = this.props;
      const entity = {
        ...paraOtherEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/para-other-sdm-suffix');
  };

  render() {
    const { paraOtherEntity, paraUsers, kmsInfos, loading, updating } = this.props;
    const { isNew } = this.state;

    const { paraValueBl, paraValueBlContentType, imageBlob, imageBlobContentType } = paraOtherEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="kmsApp.paraOther.home.createOrEditLabel">
              <Translate contentKey="kmsApp.paraOther.home.createOrEditLabel">Create or edit a ParaOther</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : paraOtherEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="para-other-sdm-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="kmsApp.paraOther.name">Name</Translate>
                  </Label>
                  <AvField
                    id="para-other-sdm-suffix-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="nameLabel">
                    <Translate contentKey="kmsApp.paraOther.help.name" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="serialNumberLabel" for="serialNumber">
                    <Translate contentKey="kmsApp.paraOther.serialNumber">Serial Number</Translate>
                  </Label>
                  <AvField
                    id="para-other-sdm-suffix-serialNumber"
                    type="text"
                    name="serialNumber"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="serialNumberLabel">
                    <Translate contentKey="kmsApp.paraOther.help.serialNumber" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="sortStringLabel" for="sortString">
                    <Translate contentKey="kmsApp.paraOther.sortString">Sort String</Translate>
                  </Label>
                  <AvField
                    id="para-other-sdm-suffix-sortString"
                    type="text"
                    name="sortString"
                    validate={{
                      maxLength: { value: 10, errorMessage: translate('entity.validation.maxlength', { max: 10 }) }
                    }}
                  />
                  <UncontrolledTooltip target="sortStringLabel">
                    <Translate contentKey="kmsApp.paraOther.help.sortString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="descStringLabel" for="descString">
                    <Translate contentKey="kmsApp.paraOther.descString">Desc String</Translate>
                  </Label>
                  <AvField
                    id="para-other-sdm-suffix-descString"
                    type="text"
                    name="descString"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="descStringLabel">
                    <Translate contentKey="kmsApp.paraOther.help.descString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="paraOtherValueTypeLabel">
                    <Translate contentKey="kmsApp.paraOther.paraOtherValueType">Para Other Value Type</Translate>
                  </Label>
                  <AvInput
                    id="para-other-sdm-suffix-paraOtherValueType"
                    type="select"
                    className="form-control"
                    name="paraOtherValueType"
                    value={(!isNew && paraOtherEntity.paraOtherValueType) || 'STRING'}
                  >
                    <option value="STRING">
                      <Translate contentKey="kmsApp.ParaOtherValueType.STRING" />
                    </option>
                    <option value="INSTANT">
                      <Translate contentKey="kmsApp.ParaOtherValueType.INSTANT" />
                    </option>
                    <option value="BOOLEAN">
                      <Translate contentKey="kmsApp.ParaOtherValueType.BOOLEAN" />
                    </option>
                    <option value="JSON">
                      <Translate contentKey="kmsApp.ParaOtherValueType.JSON" />
                    </option>
                    <option value="BLOB">
                      <Translate contentKey="kmsApp.ParaOtherValueType.BLOB" />
                    </option>
                  </AvInput>
                  <UncontrolledTooltip target="paraOtherValueTypeLabel">
                    <Translate contentKey="kmsApp.paraOther.help.paraOtherValueType" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="paraValueStLabel" for="paraValueSt">
                    <Translate contentKey="kmsApp.paraOther.paraValueSt">Para Value St</Translate>
                  </Label>
                  <AvField
                    id="para-other-sdm-suffix-paraValueSt"
                    type="text"
                    name="paraValueSt"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="paraValueStLabel">
                    <Translate contentKey="kmsApp.paraOther.help.paraValueSt" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="paraValueInLabel" for="paraValueIn">
                    <Translate contentKey="kmsApp.paraOther.paraValueIn">Para Value In</Translate>
                  </Label>
                  <AvInput
                    id="para-other-sdm-suffix-paraValueIn"
                    type="datetime-local"
                    className="form-control"
                    name="paraValueIn"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraOtherEntity.paraValueIn)}
                  />
                  <UncontrolledTooltip target="paraValueInLabel">
                    <Translate contentKey="kmsApp.paraOther.help.paraValueIn" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="paraValueBoLabel" check>
                    <AvInput id="para-other-sdm-suffix-paraValueBo" type="checkbox" className="form-control" name="paraValueBo" />
                    <Translate contentKey="kmsApp.paraOther.paraValueBo">Para Value Bo</Translate>
                  </Label>
                  <UncontrolledTooltip target="paraValueBoLabel">
                    <Translate contentKey="kmsApp.paraOther.help.paraValueBo" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="paraValueJsLabel" for="paraValueJs">
                    <Translate contentKey="kmsApp.paraOther.paraValueJs">Para Value Js</Translate>
                  </Label>
                  <AvField id="para-other-sdm-suffix-paraValueJs" type="text" name="paraValueJs" />
                  <UncontrolledTooltip target="paraValueJsLabel">
                    <Translate contentKey="kmsApp.paraOther.help.paraValueJs" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="paraValueBlLabel" for="paraValueBl">
                      <Translate contentKey="kmsApp.paraOther.paraValueBl">Para Value Bl</Translate>
                    </Label>
                    <br />
                    {paraValueBl ? (
                      <div>
                        <a onClick={openFile(paraValueBlContentType, paraValueBl)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {paraValueBlContentType}, {byteSize(paraValueBl)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('paraValueBl')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_paraValueBl" type="file" onChange={this.onBlobChange(false, 'paraValueBl')} />
                    <AvInput type="hidden" name="paraValueBl" value={paraValueBl} />
                  </AvGroup>

                  <UncontrolledTooltip target="paraValueBlLabel">
                    <Translate contentKey="kmsApp.paraOther.help.paraValueBl" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imageBlobLabel" for="imageBlob">
                      <Translate contentKey="kmsApp.paraOther.imageBlob">Image Blob</Translate>
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
                    <Translate contentKey="kmsApp.paraOther.help.imageBlob" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="imageBlobNameLabel" for="imageBlobName">
                    <Translate contentKey="kmsApp.paraOther.imageBlobName">Image Blob Name</Translate>
                  </Label>
                  <AvField
                    id="para-other-sdm-suffix-imageBlobName"
                    type="text"
                    name="imageBlobName"
                    validate={{
                      maxLength: { value: 512, errorMessage: translate('entity.validation.maxlength', { max: 512 }) }
                    }}
                  />
                  <UncontrolledTooltip target="imageBlobNameLabel">
                    <Translate contentKey="kmsApp.paraOther.help.imageBlobName" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="usingFlagLabel" check>
                    <AvInput id="para-other-sdm-suffix-usingFlag" type="checkbox" className="form-control" name="usingFlag" />
                    <Translate contentKey="kmsApp.paraOther.usingFlag">Using Flag</Translate>
                  </Label>
                  <UncontrolledTooltip target="usingFlagLabel">
                    <Translate contentKey="kmsApp.paraOther.help.usingFlag" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="remarks">
                    <Translate contentKey="kmsApp.paraOther.remarks">Remarks</Translate>
                  </Label>
                  <AvField
                    id="para-other-sdm-suffix-remarks"
                    type="text"
                    name="remarks"
                    validate={{
                      maxLength: { value: 1000, errorMessage: translate('entity.validation.maxlength', { max: 1000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="remarksLabel">
                    <Translate contentKey="kmsApp.paraOther.help.remarks" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validTypeLabel">
                    <Translate contentKey="kmsApp.paraOther.validType">Valid Type</Translate>
                  </Label>
                  <AvInput
                    id="para-other-sdm-suffix-validType"
                    type="select"
                    className="form-control"
                    name="validType"
                    value={(!isNew && paraOtherEntity.validType) || 'LONG'}
                  >
                    <option value="LONG">
                      <Translate contentKey="kmsApp.ValidType.LONG" />
                    </option>
                    <option value="SCOPE">
                      <Translate contentKey="kmsApp.ValidType.SCOPE" />
                    </option>
                  </AvInput>
                  <UncontrolledTooltip target="validTypeLabel">
                    <Translate contentKey="kmsApp.paraOther.help.validType" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validBeginLabel" for="validBegin">
                    <Translate contentKey="kmsApp.paraOther.validBegin">Valid Begin</Translate>
                  </Label>
                  <AvInput
                    id="para-other-sdm-suffix-validBegin"
                    type="datetime-local"
                    className="form-control"
                    name="validBegin"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraOtherEntity.validBegin)}
                  />
                  <UncontrolledTooltip target="validBeginLabel">
                    <Translate contentKey="kmsApp.paraOther.help.validBegin" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validEndLabel" for="validEnd">
                    <Translate contentKey="kmsApp.paraOther.validEnd">Valid End</Translate>
                  </Label>
                  <AvInput
                    id="para-other-sdm-suffix-validEnd"
                    type="datetime-local"
                    className="form-control"
                    name="validEnd"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraOtherEntity.validEnd)}
                  />
                  <UncontrolledTooltip target="validEndLabel">
                    <Translate contentKey="kmsApp.paraOther.help.validEnd" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="createTimeLabel" for="createTime">
                    <Translate contentKey="kmsApp.paraOther.createTime">Create Time</Translate>
                  </Label>
                  <AvInput
                    id="para-other-sdm-suffix-createTime"
                    type="datetime-local"
                    className="form-control"
                    name="createTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraOtherEntity.createTime)}
                  />
                  <UncontrolledTooltip target="createTimeLabel">
                    <Translate contentKey="kmsApp.paraOther.help.createTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="modifyTimeLabel" for="modifyTime">
                    <Translate contentKey="kmsApp.paraOther.modifyTime">Modify Time</Translate>
                  </Label>
                  <AvInput
                    id="para-other-sdm-suffix-modifyTime"
                    type="datetime-local"
                    className="form-control"
                    name="modifyTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraOtherEntity.modifyTime)}
                  />
                  <UncontrolledTooltip target="modifyTimeLabel">
                    <Translate contentKey="kmsApp.paraOther.help.modifyTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="verifyTimeLabel" for="verifyTime">
                    <Translate contentKey="kmsApp.paraOther.verifyTime">Verify Time</Translate>
                  </Label>
                  <AvInput
                    id="para-other-sdm-suffix-verifyTime"
                    type="datetime-local"
                    className="form-control"
                    name="verifyTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraOtherEntity.verifyTime)}
                  />
                  <UncontrolledTooltip target="verifyTimeLabel">
                    <Translate contentKey="kmsApp.paraOther.help.verifyTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label for="createdUser.name">
                    <Translate contentKey="kmsApp.paraOther.createdUser">Created User</Translate>
                  </Label>
                  <AvInput id="para-other-sdm-suffix-createdUser" type="select" className="form-control" name="createdUserId">
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
                  <Label for="modifiedUser.name">
                    <Translate contentKey="kmsApp.paraOther.modifiedUser">Modified User</Translate>
                  </Label>
                  <AvInput id="para-other-sdm-suffix-modifiedUser" type="select" className="form-control" name="modifiedUserId">
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
                  <Label for="verifiedUser.name">
                    <Translate contentKey="kmsApp.paraOther.verifiedUser">Verified User</Translate>
                  </Label>
                  <AvInput id="para-other-sdm-suffix-verifiedUser" type="select" className="form-control" name="verifiedUserId">
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
                <Button tag={Link} id="cancel-save" to="/entity/para-other-sdm-suffix" replace color="info">
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
  paraUsers: storeState.paraUser.entities,
  kmsInfos: storeState.kmsInfo.entities,
  paraOtherEntity: storeState.paraOther.entity,
  loading: storeState.paraOther.loading,
  updating: storeState.paraOther.updating,
  updateSuccess: storeState.paraOther.updateSuccess
});

const mapDispatchToProps = {
  getParaUsers,
  getKmsInfos,
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
)(ParaOtherSdmSuffixUpdate);

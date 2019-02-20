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
import { getEntities as getParaProps } from 'app/entities/para-prop-sdm-suffix/para-prop-sdm-suffix.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './para-prop-sdm-suffix.reducer';
import { IParaPropSdmSuffix } from 'app/shared/model/para-prop-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IParaPropSdmSuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IParaPropSdmSuffixUpdateState {
  isNew: boolean;
  createdUserId: string;
  modifiedUserId: string;
  verifiedUserId: string;
  parentId: string;
}

export class ParaPropSdmSuffixUpdate extends React.Component<IParaPropSdmSuffixUpdateProps, IParaPropSdmSuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdUserId: '0',
      modifiedUserId: '0',
      verifiedUserId: '0',
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

    this.props.getParaUsers();
    this.props.getParaProps();
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

    if (errors.length === 0) {
      const { paraPropEntity } = this.props;
      const entity = {
        ...paraPropEntity,
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
    this.props.history.push('/entity/para-prop-sdm-suffix');
  };

  render() {
    const { paraPropEntity, paraUsers, paraProps, loading, updating } = this.props;
    const { isNew } = this.state;

    const { imageBlob, imageBlobContentType } = paraPropEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="kmsApp.paraProp.home.createOrEditLabel">
              <Translate contentKey="kmsApp.paraProp.home.createOrEditLabel">Create or edit a ParaProp</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : paraPropEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="para-prop-sdm-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="kmsApp.paraProp.name">Name</Translate>
                  </Label>
                  <AvField
                    id="para-prop-sdm-suffix-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="nameLabel">
                    <Translate contentKey="kmsApp.paraProp.help.name" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="serialNumberLabel" for="serialNumber">
                    <Translate contentKey="kmsApp.paraProp.serialNumber">Serial Number</Translate>
                  </Label>
                  <AvField
                    id="para-prop-sdm-suffix-serialNumber"
                    type="text"
                    name="serialNumber"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="serialNumberLabel">
                    <Translate contentKey="kmsApp.paraProp.help.serialNumber" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="sortStringLabel" for="sortString">
                    <Translate contentKey="kmsApp.paraProp.sortString">Sort String</Translate>
                  </Label>
                  <AvField
                    id="para-prop-sdm-suffix-sortString"
                    type="text"
                    name="sortString"
                    validate={{
                      maxLength: { value: 10, errorMessage: translate('entity.validation.maxlength', { max: 10 }) }
                    }}
                  />
                  <UncontrolledTooltip target="sortStringLabel">
                    <Translate contentKey="kmsApp.paraProp.help.sortString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="descStringLabel" for="descString">
                    <Translate contentKey="kmsApp.paraProp.descString">Desc String</Translate>
                  </Label>
                  <AvField
                    id="para-prop-sdm-suffix-descString"
                    type="text"
                    name="descString"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="descStringLabel">
                    <Translate contentKey="kmsApp.paraProp.help.descString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imageBlobLabel" for="imageBlob">
                      <Translate contentKey="kmsApp.paraProp.imageBlob">Image Blob</Translate>
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
                    <Translate contentKey="kmsApp.paraProp.help.imageBlob" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="imageBlobNameLabel" for="imageBlobName">
                    <Translate contentKey="kmsApp.paraProp.imageBlobName">Image Blob Name</Translate>
                  </Label>
                  <AvField
                    id="para-prop-sdm-suffix-imageBlobName"
                    type="text"
                    name="imageBlobName"
                    validate={{
                      maxLength: { value: 512, errorMessage: translate('entity.validation.maxlength', { max: 512 }) }
                    }}
                  />
                  <UncontrolledTooltip target="imageBlobNameLabel">
                    <Translate contentKey="kmsApp.paraProp.help.imageBlobName" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="usingFlagLabel" check>
                    <AvInput id="para-prop-sdm-suffix-usingFlag" type="checkbox" className="form-control" name="usingFlag" />
                    <Translate contentKey="kmsApp.paraProp.usingFlag">Using Flag</Translate>
                  </Label>
                  <UncontrolledTooltip target="usingFlagLabel">
                    <Translate contentKey="kmsApp.paraProp.help.usingFlag" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="remarks">
                    <Translate contentKey="kmsApp.paraProp.remarks">Remarks</Translate>
                  </Label>
                  <AvField
                    id="para-prop-sdm-suffix-remarks"
                    type="text"
                    name="remarks"
                    validate={{
                      maxLength: { value: 1000, errorMessage: translate('entity.validation.maxlength', { max: 1000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="remarksLabel">
                    <Translate contentKey="kmsApp.paraProp.help.remarks" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validTypeLabel">
                    <Translate contentKey="kmsApp.paraProp.validType">Valid Type</Translate>
                  </Label>
                  <AvInput
                    id="para-prop-sdm-suffix-validType"
                    type="select"
                    className="form-control"
                    name="validType"
                    value={(!isNew && paraPropEntity.validType) || 'LONG'}
                  >
                    <option value="LONG">
                      <Translate contentKey="kmsApp.ValidType.LONG" />
                    </option>
                    <option value="SCOPE">
                      <Translate contentKey="kmsApp.ValidType.SCOPE" />
                    </option>
                  </AvInput>
                  <UncontrolledTooltip target="validTypeLabel">
                    <Translate contentKey="kmsApp.paraProp.help.validType" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validBeginLabel" for="validBegin">
                    <Translate contentKey="kmsApp.paraProp.validBegin">Valid Begin</Translate>
                  </Label>
                  <AvInput
                    id="para-prop-sdm-suffix-validBegin"
                    type="datetime-local"
                    className="form-control"
                    name="validBegin"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraPropEntity.validBegin)}
                  />
                  <UncontrolledTooltip target="validBeginLabel">
                    <Translate contentKey="kmsApp.paraProp.help.validBegin" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validEndLabel" for="validEnd">
                    <Translate contentKey="kmsApp.paraProp.validEnd">Valid End</Translate>
                  </Label>
                  <AvInput
                    id="para-prop-sdm-suffix-validEnd"
                    type="datetime-local"
                    className="form-control"
                    name="validEnd"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraPropEntity.validEnd)}
                  />
                  <UncontrolledTooltip target="validEndLabel">
                    <Translate contentKey="kmsApp.paraProp.help.validEnd" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="createTimeLabel" for="createTime">
                    <Translate contentKey="kmsApp.paraProp.createTime">Create Time</Translate>
                  </Label>
                  <AvInput
                    id="para-prop-sdm-suffix-createTime"
                    type="datetime-local"
                    className="form-control"
                    name="createTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraPropEntity.createTime)}
                  />
                  <UncontrolledTooltip target="createTimeLabel">
                    <Translate contentKey="kmsApp.paraProp.help.createTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="modifyTimeLabel" for="modifyTime">
                    <Translate contentKey="kmsApp.paraProp.modifyTime">Modify Time</Translate>
                  </Label>
                  <AvInput
                    id="para-prop-sdm-suffix-modifyTime"
                    type="datetime-local"
                    className="form-control"
                    name="modifyTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraPropEntity.modifyTime)}
                  />
                  <UncontrolledTooltip target="modifyTimeLabel">
                    <Translate contentKey="kmsApp.paraProp.help.modifyTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="verifyTimeLabel" for="verifyTime">
                    <Translate contentKey="kmsApp.paraProp.verifyTime">Verify Time</Translate>
                  </Label>
                  <AvInput
                    id="para-prop-sdm-suffix-verifyTime"
                    type="datetime-local"
                    className="form-control"
                    name="verifyTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraPropEntity.verifyTime)}
                  />
                  <UncontrolledTooltip target="verifyTimeLabel">
                    <Translate contentKey="kmsApp.paraProp.help.verifyTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label for="createdUser.name">
                    <Translate contentKey="kmsApp.paraProp.createdUser">Created User</Translate>
                  </Label>
                  <AvInput id="para-prop-sdm-suffix-createdUser" type="select" className="form-control" name="createdUserId">
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
                    <Translate contentKey="kmsApp.paraProp.modifiedUser">Modified User</Translate>
                  </Label>
                  <AvInput id="para-prop-sdm-suffix-modifiedUser" type="select" className="form-control" name="modifiedUserId">
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
                    <Translate contentKey="kmsApp.paraProp.verifiedUser">Verified User</Translate>
                  </Label>
                  <AvInput id="para-prop-sdm-suffix-verifiedUser" type="select" className="form-control" name="verifiedUserId">
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
                  <Label for="parent.name">
                    <Translate contentKey="kmsApp.paraProp.parent">Parent</Translate>
                  </Label>
                  <AvInput id="para-prop-sdm-suffix-parent" type="select" className="form-control" name="parentId">
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
                <Button tag={Link} id="cancel-save" to="/entity/para-prop-sdm-suffix" replace color="info">
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
  paraProps: storeState.paraProp.entities,
  paraPropEntity: storeState.paraProp.entity,
  loading: storeState.paraProp.loading,
  updating: storeState.paraProp.updating,
  updateSuccess: storeState.paraProp.updateSuccess
});

const mapDispatchToProps = {
  getParaUsers,
  getParaProps,
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
)(ParaPropSdmSuffixUpdate);

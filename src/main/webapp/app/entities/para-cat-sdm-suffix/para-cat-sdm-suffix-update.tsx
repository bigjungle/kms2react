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
import { getEntities as getParaCats } from 'app/entities/para-cat-sdm-suffix/para-cat-sdm-suffix.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './para-cat-sdm-suffix.reducer';
import { IParaCatSdmSuffix } from 'app/shared/model/para-cat-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IParaCatSdmSuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IParaCatSdmSuffixUpdateState {
  isNew: boolean;
  createdUserId: string;
  modifiedUserId: string;
  verifiedUserId: string;
  parentId: string;
}

export class ParaCatSdmSuffixUpdate extends React.Component<IParaCatSdmSuffixUpdateProps, IParaCatSdmSuffixUpdateState> {
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
    this.props.getParaCats();
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
      const { paraCatEntity } = this.props;
      const entity = {
        ...paraCatEntity,
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
    this.props.history.push('/entity/para-cat-sdm-suffix');
  };

  render() {
    const { paraCatEntity, paraUsers, paraCats, loading, updating } = this.props;
    const { isNew } = this.state;

    const { imageBlob, imageBlobContentType } = paraCatEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="kmsApp.paraCat.home.createOrEditLabel">
              <Translate contentKey="kmsApp.paraCat.home.createOrEditLabel">Create or edit a ParaCat</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : paraCatEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="para-cat-sdm-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="kmsApp.paraCat.name">Name</Translate>
                  </Label>
                  <AvField
                    id="para-cat-sdm-suffix-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="nameLabel">
                    <Translate contentKey="kmsApp.paraCat.help.name" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="serialNumberLabel" for="serialNumber">
                    <Translate contentKey="kmsApp.paraCat.serialNumber">Serial Number</Translate>
                  </Label>
                  <AvField
                    id="para-cat-sdm-suffix-serialNumber"
                    type="text"
                    name="serialNumber"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="serialNumberLabel">
                    <Translate contentKey="kmsApp.paraCat.help.serialNumber" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="sortStringLabel" for="sortString">
                    <Translate contentKey="kmsApp.paraCat.sortString">Sort String</Translate>
                  </Label>
                  <AvField
                    id="para-cat-sdm-suffix-sortString"
                    type="text"
                    name="sortString"
                    validate={{
                      maxLength: { value: 10, errorMessage: translate('entity.validation.maxlength', { max: 10 }) }
                    }}
                  />
                  <UncontrolledTooltip target="sortStringLabel">
                    <Translate contentKey="kmsApp.paraCat.help.sortString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="descStringLabel" for="descString">
                    <Translate contentKey="kmsApp.paraCat.descString">Desc String</Translate>
                  </Label>
                  <AvField
                    id="para-cat-sdm-suffix-descString"
                    type="text"
                    name="descString"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="descStringLabel">
                    <Translate contentKey="kmsApp.paraCat.help.descString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imageBlobLabel" for="imageBlob">
                      <Translate contentKey="kmsApp.paraCat.imageBlob">Image Blob</Translate>
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
                    <Translate contentKey="kmsApp.paraCat.help.imageBlob" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="imageBlobNameLabel" for="imageBlobName">
                    <Translate contentKey="kmsApp.paraCat.imageBlobName">Image Blob Name</Translate>
                  </Label>
                  <AvField
                    id="para-cat-sdm-suffix-imageBlobName"
                    type="text"
                    name="imageBlobName"
                    validate={{
                      maxLength: { value: 512, errorMessage: translate('entity.validation.maxlength', { max: 512 }) }
                    }}
                  />
                  <UncontrolledTooltip target="imageBlobNameLabel">
                    <Translate contentKey="kmsApp.paraCat.help.imageBlobName" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="usingFlagLabel" check>
                    <AvInput id="para-cat-sdm-suffix-usingFlag" type="checkbox" className="form-control" name="usingFlag" />
                    <Translate contentKey="kmsApp.paraCat.usingFlag">Using Flag</Translate>
                  </Label>
                  <UncontrolledTooltip target="usingFlagLabel">
                    <Translate contentKey="kmsApp.paraCat.help.usingFlag" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="remarks">
                    <Translate contentKey="kmsApp.paraCat.remarks">Remarks</Translate>
                  </Label>
                  <AvField
                    id="para-cat-sdm-suffix-remarks"
                    type="text"
                    name="remarks"
                    validate={{
                      maxLength: { value: 1000, errorMessage: translate('entity.validation.maxlength', { max: 1000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="remarksLabel">
                    <Translate contentKey="kmsApp.paraCat.help.remarks" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validTypeLabel">
                    <Translate contentKey="kmsApp.paraCat.validType">Valid Type</Translate>
                  </Label>
                  <AvInput
                    id="para-cat-sdm-suffix-validType"
                    type="select"
                    className="form-control"
                    name="validType"
                    value={(!isNew && paraCatEntity.validType) || 'LONG'}
                  >
                    <option value="LONG">
                      <Translate contentKey="kmsApp.ValidType.LONG" />
                    </option>
                    <option value="SCOPE">
                      <Translate contentKey="kmsApp.ValidType.SCOPE" />
                    </option>
                  </AvInput>
                  <UncontrolledTooltip target="validTypeLabel">
                    <Translate contentKey="kmsApp.paraCat.help.validType" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validBeginLabel" for="validBegin">
                    <Translate contentKey="kmsApp.paraCat.validBegin">Valid Begin</Translate>
                  </Label>
                  <AvInput
                    id="para-cat-sdm-suffix-validBegin"
                    type="datetime-local"
                    className="form-control"
                    name="validBegin"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraCatEntity.validBegin)}
                  />
                  <UncontrolledTooltip target="validBeginLabel">
                    <Translate contentKey="kmsApp.paraCat.help.validBegin" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="validEndLabel" for="validEnd">
                    <Translate contentKey="kmsApp.paraCat.validEnd">Valid End</Translate>
                  </Label>
                  <AvInput
                    id="para-cat-sdm-suffix-validEnd"
                    type="datetime-local"
                    className="form-control"
                    name="validEnd"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraCatEntity.validEnd)}
                  />
                  <UncontrolledTooltip target="validEndLabel">
                    <Translate contentKey="kmsApp.paraCat.help.validEnd" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="createTimeLabel" for="createTime">
                    <Translate contentKey="kmsApp.paraCat.createTime">Create Time</Translate>
                  </Label>
                  <AvInput
                    id="para-cat-sdm-suffix-createTime"
                    type="datetime-local"
                    className="form-control"
                    name="createTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraCatEntity.createTime)}
                  />
                  <UncontrolledTooltip target="createTimeLabel">
                    <Translate contentKey="kmsApp.paraCat.help.createTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="modifyTimeLabel" for="modifyTime">
                    <Translate contentKey="kmsApp.paraCat.modifyTime">Modify Time</Translate>
                  </Label>
                  <AvInput
                    id="para-cat-sdm-suffix-modifyTime"
                    type="datetime-local"
                    className="form-control"
                    name="modifyTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraCatEntity.modifyTime)}
                  />
                  <UncontrolledTooltip target="modifyTimeLabel">
                    <Translate contentKey="kmsApp.paraCat.help.modifyTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="verifyTimeLabel" for="verifyTime">
                    <Translate contentKey="kmsApp.paraCat.verifyTime">Verify Time</Translate>
                  </Label>
                  <AvInput
                    id="para-cat-sdm-suffix-verifyTime"
                    type="datetime-local"
                    className="form-control"
                    name="verifyTime"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.paraCatEntity.verifyTime)}
                  />
                  <UncontrolledTooltip target="verifyTimeLabel">
                    <Translate contentKey="kmsApp.paraCat.help.verifyTime" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label for="createdUser.name">
                    <Translate contentKey="kmsApp.paraCat.createdUser">Created User</Translate>
                  </Label>
                  <AvInput id="para-cat-sdm-suffix-createdUser" type="select" className="form-control" name="createdUserId">
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
                    <Translate contentKey="kmsApp.paraCat.modifiedUser">Modified User</Translate>
                  </Label>
                  <AvInput id="para-cat-sdm-suffix-modifiedUser" type="select" className="form-control" name="modifiedUserId">
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
                    <Translate contentKey="kmsApp.paraCat.verifiedUser">Verified User</Translate>
                  </Label>
                  <AvInput id="para-cat-sdm-suffix-verifiedUser" type="select" className="form-control" name="verifiedUserId">
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
                    <Translate contentKey="kmsApp.paraCat.parent">Parent</Translate>
                  </Label>
                  <AvInput id="para-cat-sdm-suffix-parent" type="select" className="form-control" name="parentId">
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
                <Button tag={Link} id="cancel-save" to="/entity/para-cat-sdm-suffix" replace color="info">
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
  paraCats: storeState.paraCat.entities,
  paraCatEntity: storeState.paraCat.entity,
  loading: storeState.paraCat.loading,
  updating: storeState.paraCat.updating,
  updateSuccess: storeState.paraCat.updateSuccess
});

const mapDispatchToProps = {
  getParaUsers,
  getParaCats,
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
)(ParaCatSdmSuffixUpdate);

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
import { getEntities as getParaNodes } from 'app/entities/para-node-sdm-suffix/para-node-sdm-suffix.reducer';
import { IParaRoleSdmSuffix } from 'app/shared/model/para-role-sdm-suffix.model';
import { getEntities as getParaRoles } from 'app/entities/para-role-sdm-suffix/para-role-sdm-suffix.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './para-node-sdm-suffix.reducer';
import { IParaNodeSdmSuffix } from 'app/shared/model/para-node-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IParaNodeSdmSuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IParaNodeSdmSuffixUpdateState {
  isNew: boolean;
  createdUserId: string;
  modifiedUserId: string;
  verifiedUserId: string;
  parentId: string;
  paraRoleId: string;
}

export class ParaNodeSdmSuffixUpdate extends React.Component<IParaNodeSdmSuffixUpdateProps, IParaNodeSdmSuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      createdUserId: '0',
      modifiedUserId: '0',
      verifiedUserId: '0',
      parentId: '0',
      paraRoleId: '0',
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
    this.props.getParaNodes();
    this.props.getParaRoles();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { paraNodeEntity } = this.props;
      const entity = {
        ...paraNodeEntity,
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
    this.props.history.push('/entity/para-node-sdm-suffix');
  };

  render() {
    const { paraNodeEntity, paraUsers, paraNodes, paraRoles, loading, updating } = this.props;
    const { isNew } = this.state;

    const { imageBlob, imageBlobContentType } = paraNodeEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="kmsApp.paraNode.home.createOrEditLabel">
              <Translate contentKey="kmsApp.paraNode.home.createOrEditLabel">Create or edit a ParaNode</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : paraNodeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="para-node-sdm-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    <Translate contentKey="kmsApp.paraNode.name">Name</Translate>
                  </Label>
                  <AvField
                    id="para-node-sdm-suffix-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="nameLabel">
                    <Translate contentKey="kmsApp.paraNode.help.name" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="linkLabel" for="link">
                    <Translate contentKey="kmsApp.paraNode.link">Link</Translate>
                  </Label>
                  <AvField
                    id="para-node-sdm-suffix-link"
                    type="text"
                    name="link"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="linkLabel">
                    <Translate contentKey="kmsApp.paraNode.help.link" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="serialNumberLabel" for="serialNumber">
                    <Translate contentKey="kmsApp.paraNode.serialNumber">Serial Number</Translate>
                  </Label>
                  <AvField
                    id="para-node-sdm-suffix-serialNumber"
                    type="text"
                    name="serialNumber"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="serialNumberLabel">
                    <Translate contentKey="kmsApp.paraNode.help.serialNumber" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="sortStringLabel" for="sortString">
                    <Translate contentKey="kmsApp.paraNode.sortString">Sort String</Translate>
                  </Label>
                  <AvField
                    id="para-node-sdm-suffix-sortString"
                    type="text"
                    name="sortString"
                    validate={{
                      maxLength: { value: 10, errorMessage: translate('entity.validation.maxlength', { max: 10 }) }
                    }}
                  />
                  <UncontrolledTooltip target="sortStringLabel">
                    <Translate contentKey="kmsApp.paraNode.help.sortString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="descStringLabel" for="descString">
                    <Translate contentKey="kmsApp.paraNode.descString">Desc String</Translate>
                  </Label>
                  <AvField
                    id="para-node-sdm-suffix-descString"
                    type="text"
                    name="descString"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                  <UncontrolledTooltip target="descStringLabel">
                    <Translate contentKey="kmsApp.paraNode.help.descString" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imageBlobLabel" for="imageBlob">
                      <Translate contentKey="kmsApp.paraNode.imageBlob">Image Blob</Translate>
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
                    <Translate contentKey="kmsApp.paraNode.help.imageBlob" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="imageBlobNameLabel" for="imageBlobName">
                    <Translate contentKey="kmsApp.paraNode.imageBlobName">Image Blob Name</Translate>
                  </Label>
                  <AvField
                    id="para-node-sdm-suffix-imageBlobName"
                    type="text"
                    name="imageBlobName"
                    validate={{
                      maxLength: { value: 512, errorMessage: translate('entity.validation.maxlength', { max: 512 }) }
                    }}
                  />
                  <UncontrolledTooltip target="imageBlobNameLabel">
                    <Translate contentKey="kmsApp.paraNode.help.imageBlobName" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="usingFlagLabel" check>
                    <AvInput id="para-node-sdm-suffix-usingFlag" type="checkbox" className="form-control" name="usingFlag" />
                    <Translate contentKey="kmsApp.paraNode.usingFlag">Using Flag</Translate>
                  </Label>
                  <UncontrolledTooltip target="usingFlagLabel">
                    <Translate contentKey="kmsApp.paraNode.help.usingFlag" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="remarks">
                    <Translate contentKey="kmsApp.paraNode.remarks">Remarks</Translate>
                  </Label>
                  <AvField
                    id="para-node-sdm-suffix-remarks"
                    type="text"
                    name="remarks"
                    validate={{
                      maxLength: { value: 1000, errorMessage: translate('entity.validation.maxlength', { max: 1000 }) }
                    }}
                  />
                  <UncontrolledTooltip target="remarksLabel">
                    <Translate contentKey="kmsApp.paraNode.help.remarks" />
                  </UncontrolledTooltip>
                </AvGroup>
                <AvGroup>
                  <Label for="createdUser.name">
                    <Translate contentKey="kmsApp.paraNode.createdUser">Created User</Translate>
                  </Label>
                  <AvInput id="para-node-sdm-suffix-createdUser" type="select" className="form-control" name="createdUserId">
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
                    <Translate contentKey="kmsApp.paraNode.modifiedUser">Modified User</Translate>
                  </Label>
                  <AvInput id="para-node-sdm-suffix-modifiedUser" type="select" className="form-control" name="modifiedUserId">
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
                    <Translate contentKey="kmsApp.paraNode.verifiedUser">Verified User</Translate>
                  </Label>
                  <AvInput id="para-node-sdm-suffix-verifiedUser" type="select" className="form-control" name="verifiedUserId">
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
                    <Translate contentKey="kmsApp.paraNode.parent">Parent</Translate>
                  </Label>
                  <AvInput id="para-node-sdm-suffix-parent" type="select" className="form-control" name="parentId">
                    <option value="" key="0" />
                    {paraNodes
                      ? paraNodes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/para-node-sdm-suffix" replace color="info">
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
  paraNodes: storeState.paraNode.entities,
  paraRoles: storeState.paraRole.entities,
  paraNodeEntity: storeState.paraNode.entity,
  loading: storeState.paraNode.loading,
  updating: storeState.paraNode.updating,
  updateSuccess: storeState.paraNode.updateSuccess
});

const mapDispatchToProps = {
  getParaUsers,
  getParaNodes,
  getParaRoles,
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
)(ParaNodeSdmSuffixUpdate);

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './para-role-sdm-suffix.reducer';
import { IParaRoleSdmSuffix } from 'app/shared/model/para-role-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParaRoleSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ParaRoleSdmSuffixDetail extends React.Component<IParaRoleSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { paraRoleEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.paraRole.detail.title">ParaRole</Translate> [<b>{paraRoleEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.paraRole.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.paraRole.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraRoleEntity.name}</dd>
            <dt>
              <span id="serialNumber">
                <Translate contentKey="kmsApp.paraRole.serialNumber">Serial Number</Translate>
              </span>
              <UncontrolledTooltip target="serialNumber">
                <Translate contentKey="kmsApp.paraRole.help.serialNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraRoleEntity.serialNumber}</dd>
            <dt>
              <span id="sortString">
                <Translate contentKey="kmsApp.paraRole.sortString">Sort String</Translate>
              </span>
              <UncontrolledTooltip target="sortString">
                <Translate contentKey="kmsApp.paraRole.help.sortString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraRoleEntity.sortString}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.paraRole.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.paraRole.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraRoleEntity.descString}</dd>
            <dt>
              <span id="imageBlob">
                <Translate contentKey="kmsApp.paraRole.imageBlob">Image Blob</Translate>
              </span>
              <UncontrolledTooltip target="imageBlob">
                <Translate contentKey="kmsApp.paraRole.help.imageBlob" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              {paraRoleEntity.imageBlob ? (
                <div>
                  <a onClick={openFile(paraRoleEntity.imageBlobContentType, paraRoleEntity.imageBlob)}>
                    <img
                      src={`data:${paraRoleEntity.imageBlobContentType};base64,${paraRoleEntity.imageBlob}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {paraRoleEntity.imageBlobContentType}, {byteSize(paraRoleEntity.imageBlob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="imageBlobName">
                <Translate contentKey="kmsApp.paraRole.imageBlobName">Image Blob Name</Translate>
              </span>
              <UncontrolledTooltip target="imageBlobName">
                <Translate contentKey="kmsApp.paraRole.help.imageBlobName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraRoleEntity.imageBlobName}</dd>
            <dt>
              <span id="usingFlag">
                <Translate contentKey="kmsApp.paraRole.usingFlag">Using Flag</Translate>
              </span>
              <UncontrolledTooltip target="usingFlag">
                <Translate contentKey="kmsApp.paraRole.help.usingFlag" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraRoleEntity.usingFlag ? 'true' : 'false'}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.paraRole.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.paraRole.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraRoleEntity.remarks}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraRole.createdUser">Created User</Translate>
            </dt>
            <dd>{paraRoleEntity.createdUserName ? paraRoleEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraRole.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{paraRoleEntity.modifiedUserName ? paraRoleEntity.modifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraRole.verifiedUser">Verified User</Translate>
            </dt>
            <dd>{paraRoleEntity.verifiedUserName ? paraRoleEntity.verifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraRole.parent">Parent</Translate>
            </dt>
            <dd>{paraRoleEntity.parentName ? paraRoleEntity.parentName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraRole.paraNode">Para Node</Translate>
            </dt>
            <dd>
              {paraRoleEntity.paraNodes
                ? paraRoleEntity.paraNodes.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.name}</a>
                      {i === paraRoleEntity.paraNodes.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/para-role-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/para-role-sdm-suffix/${paraRoleEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ paraRole }: IRootState) => ({
  paraRoleEntity: paraRole.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParaRoleSdmSuffixDetail);

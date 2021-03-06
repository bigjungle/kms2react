import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './para-cat-sdm-suffix.reducer';
import { IParaCatSdmSuffix } from 'app/shared/model/para-cat-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParaCatSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ParaCatSdmSuffixDetail extends React.Component<IParaCatSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { paraCatEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.paraCat.detail.title">ParaCat</Translate> [<b>{paraCatEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.paraCat.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.paraCat.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraCatEntity.name}</dd>
            <dt>
              <span id="serialNumber">
                <Translate contentKey="kmsApp.paraCat.serialNumber">Serial Number</Translate>
              </span>
              <UncontrolledTooltip target="serialNumber">
                <Translate contentKey="kmsApp.paraCat.help.serialNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraCatEntity.serialNumber}</dd>
            <dt>
              <span id="sortString">
                <Translate contentKey="kmsApp.paraCat.sortString">Sort String</Translate>
              </span>
              <UncontrolledTooltip target="sortString">
                <Translate contentKey="kmsApp.paraCat.help.sortString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraCatEntity.sortString}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.paraCat.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.paraCat.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraCatEntity.descString}</dd>
            <dt>
              <span id="imageBlob">
                <Translate contentKey="kmsApp.paraCat.imageBlob">Image Blob</Translate>
              </span>
              <UncontrolledTooltip target="imageBlob">
                <Translate contentKey="kmsApp.paraCat.help.imageBlob" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              {paraCatEntity.imageBlob ? (
                <div>
                  <a onClick={openFile(paraCatEntity.imageBlobContentType, paraCatEntity.imageBlob)}>
                    <img
                      src={`data:${paraCatEntity.imageBlobContentType};base64,${paraCatEntity.imageBlob}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {paraCatEntity.imageBlobContentType}, {byteSize(paraCatEntity.imageBlob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="imageBlobName">
                <Translate contentKey="kmsApp.paraCat.imageBlobName">Image Blob Name</Translate>
              </span>
              <UncontrolledTooltip target="imageBlobName">
                <Translate contentKey="kmsApp.paraCat.help.imageBlobName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraCatEntity.imageBlobName}</dd>
            <dt>
              <span id="usingFlag">
                <Translate contentKey="kmsApp.paraCat.usingFlag">Using Flag</Translate>
              </span>
              <UncontrolledTooltip target="usingFlag">
                <Translate contentKey="kmsApp.paraCat.help.usingFlag" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraCatEntity.usingFlag ? 'true' : 'false'}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.paraCat.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.paraCat.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraCatEntity.remarks}</dd>
            <dt>
              <span id="validType">
                <Translate contentKey="kmsApp.paraCat.validType">Valid Type</Translate>
              </span>
              <UncontrolledTooltip target="validType">
                <Translate contentKey="kmsApp.paraCat.help.validType" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraCatEntity.validType}</dd>
            <dt>
              <span id="validBegin">
                <Translate contentKey="kmsApp.paraCat.validBegin">Valid Begin</Translate>
              </span>
              <UncontrolledTooltip target="validBegin">
                <Translate contentKey="kmsApp.paraCat.help.validBegin" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraCatEntity.validBegin} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="validEnd">
                <Translate contentKey="kmsApp.paraCat.validEnd">Valid End</Translate>
              </span>
              <UncontrolledTooltip target="validEnd">
                <Translate contentKey="kmsApp.paraCat.help.validEnd" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraCatEntity.validEnd} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="createTime">
                <Translate contentKey="kmsApp.paraCat.createTime">Create Time</Translate>
              </span>
              <UncontrolledTooltip target="createTime">
                <Translate contentKey="kmsApp.paraCat.help.createTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraCatEntity.createTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="modifyTime">
                <Translate contentKey="kmsApp.paraCat.modifyTime">Modify Time</Translate>
              </span>
              <UncontrolledTooltip target="modifyTime">
                <Translate contentKey="kmsApp.paraCat.help.modifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraCatEntity.modifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="verifyTime">
                <Translate contentKey="kmsApp.paraCat.verifyTime">Verify Time</Translate>
              </span>
              <UncontrolledTooltip target="verifyTime">
                <Translate contentKey="kmsApp.paraCat.help.verifyTime" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              <TextFormat value={paraCatEntity.verifyTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="kmsApp.paraCat.createdUser">Created User</Translate>
            </dt>
            <dd>{paraCatEntity.createdUserName ? paraCatEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraCat.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{paraCatEntity.modifiedUserName ? paraCatEntity.modifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraCat.verifiedUser">Verified User</Translate>
            </dt>
            <dd>{paraCatEntity.verifiedUserName ? paraCatEntity.verifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraCat.parent">Parent</Translate>
            </dt>
            <dd>{paraCatEntity.parentName ? paraCatEntity.parentName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/para-cat-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/para-cat-sdm-suffix/${paraCatEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ paraCat }: IRootState) => ({
  paraCatEntity: paraCat.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParaCatSdmSuffixDetail);

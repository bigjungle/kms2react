import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './para-node-sdm-suffix.reducer';
import { IParaNodeSdmSuffix } from 'app/shared/model/para-node-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IParaNodeSdmSuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ParaNodeSdmSuffixDetail extends React.Component<IParaNodeSdmSuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { paraNodeEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="kmsApp.paraNode.detail.title">ParaNode</Translate> [<b>{paraNodeEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="kmsApp.paraNode.name">Name</Translate>
              </span>
              <UncontrolledTooltip target="name">
                <Translate contentKey="kmsApp.paraNode.help.name" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraNodeEntity.name}</dd>
            <dt>
              <span id="link">
                <Translate contentKey="kmsApp.paraNode.link">Link</Translate>
              </span>
              <UncontrolledTooltip target="link">
                <Translate contentKey="kmsApp.paraNode.help.link" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraNodeEntity.link}</dd>
            <dt>
              <span id="serialNumber">
                <Translate contentKey="kmsApp.paraNode.serialNumber">Serial Number</Translate>
              </span>
              <UncontrolledTooltip target="serialNumber">
                <Translate contentKey="kmsApp.paraNode.help.serialNumber" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraNodeEntity.serialNumber}</dd>
            <dt>
              <span id="sortString">
                <Translate contentKey="kmsApp.paraNode.sortString">Sort String</Translate>
              </span>
              <UncontrolledTooltip target="sortString">
                <Translate contentKey="kmsApp.paraNode.help.sortString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraNodeEntity.sortString}</dd>
            <dt>
              <span id="descString">
                <Translate contentKey="kmsApp.paraNode.descString">Desc String</Translate>
              </span>
              <UncontrolledTooltip target="descString">
                <Translate contentKey="kmsApp.paraNode.help.descString" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraNodeEntity.descString}</dd>
            <dt>
              <span id="imageBlob">
                <Translate contentKey="kmsApp.paraNode.imageBlob">Image Blob</Translate>
              </span>
              <UncontrolledTooltip target="imageBlob">
                <Translate contentKey="kmsApp.paraNode.help.imageBlob" />
              </UncontrolledTooltip>
            </dt>
            <dd>
              {paraNodeEntity.imageBlob ? (
                <div>
                  <a onClick={openFile(paraNodeEntity.imageBlobContentType, paraNodeEntity.imageBlob)}>
                    <img
                      src={`data:${paraNodeEntity.imageBlobContentType};base64,${paraNodeEntity.imageBlob}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                  <span>
                    {paraNodeEntity.imageBlobContentType}, {byteSize(paraNodeEntity.imageBlob)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>
              <span id="imageBlobName">
                <Translate contentKey="kmsApp.paraNode.imageBlobName">Image Blob Name</Translate>
              </span>
              <UncontrolledTooltip target="imageBlobName">
                <Translate contentKey="kmsApp.paraNode.help.imageBlobName" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraNodeEntity.imageBlobName}</dd>
            <dt>
              <span id="usingFlag">
                <Translate contentKey="kmsApp.paraNode.usingFlag">Using Flag</Translate>
              </span>
              <UncontrolledTooltip target="usingFlag">
                <Translate contentKey="kmsApp.paraNode.help.usingFlag" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraNodeEntity.usingFlag ? 'true' : 'false'}</dd>
            <dt>
              <span id="remarks">
                <Translate contentKey="kmsApp.paraNode.remarks">Remarks</Translate>
              </span>
              <UncontrolledTooltip target="remarks">
                <Translate contentKey="kmsApp.paraNode.help.remarks" />
              </UncontrolledTooltip>
            </dt>
            <dd>{paraNodeEntity.remarks}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraNode.createdUser">Created User</Translate>
            </dt>
            <dd>{paraNodeEntity.createdUserName ? paraNodeEntity.createdUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraNode.modifiedUser">Modified User</Translate>
            </dt>
            <dd>{paraNodeEntity.modifiedUserName ? paraNodeEntity.modifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraNode.verifiedUser">Verified User</Translate>
            </dt>
            <dd>{paraNodeEntity.verifiedUserName ? paraNodeEntity.verifiedUserName : ''}</dd>
            <dt>
              <Translate contentKey="kmsApp.paraNode.parent">Parent</Translate>
            </dt>
            <dd>{paraNodeEntity.parentName ? paraNodeEntity.parentName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/para-node-sdm-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/para-node-sdm-suffix/${paraNodeEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ paraNode }: IRootState) => ({
  paraNodeEntity: paraNode.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ParaNodeSdmSuffixDetail);

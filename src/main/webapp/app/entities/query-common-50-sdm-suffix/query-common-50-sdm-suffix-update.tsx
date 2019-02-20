import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './query-common-50-sdm-suffix.reducer';
import { IQueryCommon50SdmSuffix } from 'app/shared/model/query-common-50-sdm-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IQueryCommon50SdmSuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IQueryCommon50SdmSuffixUpdateState {
  isNew: boolean;
}

export class QueryCommon50SdmSuffixUpdate extends React.Component<IQueryCommon50SdmSuffixUpdateProps, IQueryCommon50SdmSuffixUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    values.queryDate = convertDateTimeToServer(values.queryDate);

    if (errors.length === 0) {
      const { queryCommon50Entity } = this.props;
      const entity = {
        ...queryCommon50Entity,
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
    this.props.history.push('/entity/query-common-50-sdm-suffix');
  };

  render() {
    const { queryCommon50Entity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="kmsApp.queryCommon50.home.createOrEditLabel">
              <Translate contentKey="kmsApp.queryCommon50.home.createOrEditLabel">Create or edit a QueryCommon50</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : queryCommon50Entity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="query-common-50-sdm-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="queryNameLabel" for="queryName">
                    <Translate contentKey="kmsApp.queryCommon50.queryName">Query Name</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-queryName"
                    type="text"
                    name="queryName"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="queryDateLabel" for="queryDate">
                    <Translate contentKey="kmsApp.queryCommon50.queryDate">Query Date</Translate>
                  </Label>
                  <AvInput
                    id="query-common-50-sdm-suffix-queryDate"
                    type="datetime-local"
                    className="form-control"
                    name="queryDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.queryCommon50Entity.queryDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="queryUserLabel" for="queryUser">
                    <Translate contentKey="kmsApp.queryCommon50.queryUser">Query User</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-queryUser"
                    type="text"
                    name="queryUser"
                    validate={{
                      maxLength: { value: 256, errorMessage: translate('entity.validation.maxlength', { max: 256 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col01Label" for="col01">
                    <Translate contentKey="kmsApp.queryCommon50.col01">Col 01</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col01"
                    type="text"
                    name="col01"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col02Label" for="col02">
                    <Translate contentKey="kmsApp.queryCommon50.col02">Col 02</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col02"
                    type="text"
                    name="col02"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col03Label" for="col03">
                    <Translate contentKey="kmsApp.queryCommon50.col03">Col 03</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col03"
                    type="text"
                    name="col03"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col04Label" for="col04">
                    <Translate contentKey="kmsApp.queryCommon50.col04">Col 04</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col04"
                    type="text"
                    name="col04"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col05Label" for="col05">
                    <Translate contentKey="kmsApp.queryCommon50.col05">Col 05</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col05"
                    type="text"
                    name="col05"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col06Label" for="col06">
                    <Translate contentKey="kmsApp.queryCommon50.col06">Col 06</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col06"
                    type="text"
                    name="col06"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col07Label" for="col07">
                    <Translate contentKey="kmsApp.queryCommon50.col07">Col 07</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col07"
                    type="text"
                    name="col07"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col08Label" for="col08">
                    <Translate contentKey="kmsApp.queryCommon50.col08">Col 08</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col08"
                    type="text"
                    name="col08"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col09Label" for="col09">
                    <Translate contentKey="kmsApp.queryCommon50.col09">Col 09</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col09"
                    type="text"
                    name="col09"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col10Label" for="col10">
                    <Translate contentKey="kmsApp.queryCommon50.col10">Col 10</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col10"
                    type="text"
                    name="col10"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col11Label" for="col11">
                    <Translate contentKey="kmsApp.queryCommon50.col11">Col 11</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col11"
                    type="text"
                    name="col11"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col12Label" for="col12">
                    <Translate contentKey="kmsApp.queryCommon50.col12">Col 12</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col12"
                    type="text"
                    name="col12"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col13Label" for="col13">
                    <Translate contentKey="kmsApp.queryCommon50.col13">Col 13</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col13"
                    type="text"
                    name="col13"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col14Label" for="col14">
                    <Translate contentKey="kmsApp.queryCommon50.col14">Col 14</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col14"
                    type="text"
                    name="col14"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col15Label" for="col15">
                    <Translate contentKey="kmsApp.queryCommon50.col15">Col 15</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col15"
                    type="text"
                    name="col15"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col16Label" for="col16">
                    <Translate contentKey="kmsApp.queryCommon50.col16">Col 16</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col16"
                    type="text"
                    name="col16"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col17Label" for="col17">
                    <Translate contentKey="kmsApp.queryCommon50.col17">Col 17</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col17"
                    type="text"
                    name="col17"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col18Label" for="col18">
                    <Translate contentKey="kmsApp.queryCommon50.col18">Col 18</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col18"
                    type="text"
                    name="col18"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col19Label" for="col19">
                    <Translate contentKey="kmsApp.queryCommon50.col19">Col 19</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col19"
                    type="text"
                    name="col19"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col20Label" for="col20">
                    <Translate contentKey="kmsApp.queryCommon50.col20">Col 20</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col20"
                    type="text"
                    name="col20"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col21Label" for="col21">
                    <Translate contentKey="kmsApp.queryCommon50.col21">Col 21</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col21"
                    type="text"
                    name="col21"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col22Label" for="col22">
                    <Translate contentKey="kmsApp.queryCommon50.col22">Col 22</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col22"
                    type="text"
                    name="col22"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col23Label" for="col23">
                    <Translate contentKey="kmsApp.queryCommon50.col23">Col 23</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col23"
                    type="text"
                    name="col23"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col24Label" for="col24">
                    <Translate contentKey="kmsApp.queryCommon50.col24">Col 24</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col24"
                    type="text"
                    name="col24"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col25Label" for="col25">
                    <Translate contentKey="kmsApp.queryCommon50.col25">Col 25</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col25"
                    type="text"
                    name="col25"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col26Label" for="col26">
                    <Translate contentKey="kmsApp.queryCommon50.col26">Col 26</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col26"
                    type="text"
                    name="col26"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col27Label" for="col27">
                    <Translate contentKey="kmsApp.queryCommon50.col27">Col 27</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col27"
                    type="text"
                    name="col27"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col28Label" for="col28">
                    <Translate contentKey="kmsApp.queryCommon50.col28">Col 28</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col28"
                    type="text"
                    name="col28"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col29Label" for="col29">
                    <Translate contentKey="kmsApp.queryCommon50.col29">Col 29</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col29"
                    type="text"
                    name="col29"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col30Label" for="col30">
                    <Translate contentKey="kmsApp.queryCommon50.col30">Col 30</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col30"
                    type="text"
                    name="col30"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col31Label" for="col31">
                    <Translate contentKey="kmsApp.queryCommon50.col31">Col 31</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col31"
                    type="text"
                    name="col31"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col32Label" for="col32">
                    <Translate contentKey="kmsApp.queryCommon50.col32">Col 32</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col32"
                    type="text"
                    name="col32"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col33Label" for="col33">
                    <Translate contentKey="kmsApp.queryCommon50.col33">Col 33</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col33"
                    type="text"
                    name="col33"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col34Label" for="col34">
                    <Translate contentKey="kmsApp.queryCommon50.col34">Col 34</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col34"
                    type="text"
                    name="col34"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col35Label" for="col35">
                    <Translate contentKey="kmsApp.queryCommon50.col35">Col 35</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col35"
                    type="text"
                    name="col35"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col36Label" for="col36">
                    <Translate contentKey="kmsApp.queryCommon50.col36">Col 36</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col36"
                    type="text"
                    name="col36"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col37Label" for="col37">
                    <Translate contentKey="kmsApp.queryCommon50.col37">Col 37</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col37"
                    type="text"
                    name="col37"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col38Label" for="col38">
                    <Translate contentKey="kmsApp.queryCommon50.col38">Col 38</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col38"
                    type="text"
                    name="col38"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col39Label" for="col39">
                    <Translate contentKey="kmsApp.queryCommon50.col39">Col 39</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col39"
                    type="text"
                    name="col39"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col40Label" for="col40">
                    <Translate contentKey="kmsApp.queryCommon50.col40">Col 40</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col40"
                    type="text"
                    name="col40"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col41Label" for="col41">
                    <Translate contentKey="kmsApp.queryCommon50.col41">Col 41</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col41"
                    type="text"
                    name="col41"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col42Label" for="col42">
                    <Translate contentKey="kmsApp.queryCommon50.col42">Col 42</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col42"
                    type="text"
                    name="col42"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col43Label" for="col43">
                    <Translate contentKey="kmsApp.queryCommon50.col43">Col 43</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col43"
                    type="text"
                    name="col43"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col44Label" for="col44">
                    <Translate contentKey="kmsApp.queryCommon50.col44">Col 44</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col44"
                    type="text"
                    name="col44"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col45Label" for="col45">
                    <Translate contentKey="kmsApp.queryCommon50.col45">Col 45</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col45"
                    type="text"
                    name="col45"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col46Label" for="col46">
                    <Translate contentKey="kmsApp.queryCommon50.col46">Col 46</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col46"
                    type="text"
                    name="col46"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col47Label" for="col47">
                    <Translate contentKey="kmsApp.queryCommon50.col47">Col 47</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col47"
                    type="text"
                    name="col47"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col48Label" for="col48">
                    <Translate contentKey="kmsApp.queryCommon50.col48">Col 48</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col48"
                    type="text"
                    name="col48"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col49Label" for="col49">
                    <Translate contentKey="kmsApp.queryCommon50.col49">Col 49</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col49"
                    type="text"
                    name="col49"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="col50Label" for="col50">
                    <Translate contentKey="kmsApp.queryCommon50.col50">Col 50</Translate>
                  </Label>
                  <AvField
                    id="query-common-50-sdm-suffix-col50"
                    type="text"
                    name="col50"
                    validate={{
                      maxLength: { value: 4000, errorMessage: translate('entity.validation.maxlength', { max: 4000 }) }
                    }}
                  />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/query-common-50-sdm-suffix" replace color="info">
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
  queryCommon50Entity: storeState.queryCommon50.entity,
  loading: storeState.queryCommon50.loading,
  updating: storeState.queryCommon50.updating,
  updateSuccess: storeState.queryCommon50.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueryCommon50SdmSuffixUpdate);

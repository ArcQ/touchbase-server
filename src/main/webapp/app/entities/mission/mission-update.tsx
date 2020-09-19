import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './mission.reducer';
import { IMission } from 'app/shared/model/mission.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMissionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MissionUpdate = (props: IMissionUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { missionEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/mission');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...missionEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="touchbaseApp.mission.home.createOrEditLabel">
            <Translate contentKey="touchbaseApp.mission.home.createOrEditLabel">Create or edit a Mission</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : missionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="mission-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="mission-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="mission-name">
                  <Translate contentKey="touchbaseApp.mission.name">Name</Translate>
                </Label>
                <AvField id="mission-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="mission-description">
                  <Translate contentKey="touchbaseApp.mission.description">Description</Translate>
                </Label>
                <AvField id="mission-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="scoreRewardLabel" for="mission-scoreReward">
                  <Translate contentKey="touchbaseApp.mission.scoreReward">Score Reward</Translate>
                </Label>
                <AvField id="mission-scoreReward" type="text" name="scoreReward" />
              </AvGroup>
              <AvGroup>
                <Label id="missionTypeLabel" for="mission-missionType">
                  <Translate contentKey="touchbaseApp.mission.missionType">Mission Type</Translate>
                </Label>
                <AvInput
                  id="mission-missionType"
                  type="select"
                  className="form-control"
                  name="missionType"
                  value={(!isNew && missionEntity.missionType) || 'PERIODIC'}
                >
                  <option value="PERIODIC">{translate('touchbaseApp.MissionType.PERIODIC')}</option>
                  <option value="WEEKLY">{translate('touchbaseApp.MissionType.WEEKLY')}</option>
                  <option value="ONE_TIME">{translate('touchbaseApp.MissionType.ONE_TIME')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/mission" replace color="info">
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
};

const mapStateToProps = (storeState: IRootState) => ({
  missionEntity: storeState.mission.entity,
  loading: storeState.mission.loading,
  updating: storeState.mission.updating,
  updateSuccess: storeState.mission.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MissionUpdate);

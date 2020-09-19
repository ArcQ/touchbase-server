import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './base.reducer';
import { IBase } from 'app/shared/model/base.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBaseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BaseUpdate = (props: IBaseUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { baseEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/base');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    if (errors.length === 0) {
      const entity = {
        ...baseEntity,
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
          <h2 id="touchbaseApp.base.home.createOrEditLabel">
            <Translate contentKey="touchbaseApp.base.home.createOrEditLabel">Create or edit a Base</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : baseEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="base-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="base-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="createdDateLabel" for="base-createdDate">
                  <Translate contentKey="touchbaseApp.base.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="base-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.baseEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedDateLabel" for="base-lastModifiedDate">
                  <Translate contentKey="touchbaseApp.base.lastModifiedDate">Last Modified Date</Translate>
                </Label>
                <AvInput
                  id="base-lastModifiedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastModifiedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.baseEntity.lastModifiedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="base-name">
                  <Translate contentKey="touchbaseApp.base.name">Name</Translate>
                </Label>
                <AvField
                  id="base-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="scoreLabel" for="base-score">
                  <Translate contentKey="touchbaseApp.base.score">Score</Translate>
                </Label>
                <AvField id="base-score" type="string" className="form-control" name="score" />
              </AvGroup>
              <AvGroup>
                <Label id="imageUrlLabel" for="base-imageUrl">
                  <Translate contentKey="touchbaseApp.base.imageUrl">Image Url</Translate>
                </Label>
                <AvField
                  id="base-imageUrl"
                  type="text"
                  name="imageUrl"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isActiveLabel">
                  <AvInput id="base-isActive" type="checkbox" className="form-check-input" name="isActive" />
                  <Translate contentKey="touchbaseApp.base.isActive">Is Active</Translate>
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/base" replace color="info">
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
  baseEntity: storeState.base.entity,
  loading: storeState.base.loading,
  updating: storeState.base.updating,
  updateSuccess: storeState.base.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BaseUpdate);

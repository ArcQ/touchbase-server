import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBaseMember, defaultValue } from 'app/shared/model/base-member.model';

export const ACTION_TYPES = {
  FETCH_BASEMEMBER_LIST: 'baseMember/FETCH_BASEMEMBER_LIST',
  FETCH_BASEMEMBER: 'baseMember/FETCH_BASEMEMBER',
  CREATE_BASEMEMBER: 'baseMember/CREATE_BASEMEMBER',
  UPDATE_BASEMEMBER: 'baseMember/UPDATE_BASEMEMBER',
  DELETE_BASEMEMBER: 'baseMember/DELETE_BASEMEMBER',
  RESET: 'baseMember/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBaseMember>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type BaseMemberState = Readonly<typeof initialState>;

// Reducer

export default (state: BaseMemberState = initialState, action): BaseMemberState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BASEMEMBER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BASEMEMBER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_BASEMEMBER):
    case REQUEST(ACTION_TYPES.UPDATE_BASEMEMBER):
    case REQUEST(ACTION_TYPES.DELETE_BASEMEMBER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_BASEMEMBER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BASEMEMBER):
    case FAILURE(ACTION_TYPES.CREATE_BASEMEMBER):
    case FAILURE(ACTION_TYPES.UPDATE_BASEMEMBER):
    case FAILURE(ACTION_TYPES.DELETE_BASEMEMBER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASEMEMBER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_BASEMEMBER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_BASEMEMBER):
    case SUCCESS(ACTION_TYPES.UPDATE_BASEMEMBER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_BASEMEMBER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/base-members';

// Actions

export const getEntities: ICrudGetAllAction<IBaseMember> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BASEMEMBER_LIST,
  payload: axios.get<IBaseMember>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IBaseMember> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BASEMEMBER,
    payload: axios.get<IBaseMember>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IBaseMember> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BASEMEMBER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBaseMember> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BASEMEMBER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBaseMember> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BASEMEMBER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
